package com.example.acpractica1.ui.screens.home

// Architecture components (lifecycle)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
// Forma de generar viewmodel heredando de ViewModel
class HomeViewModel @Inject constructor(
    // Property que enlaza objeto de tipo CountriesRepository
    // que contenía los métodos fecthAllCountries() y fetchCountriesByCont()
    // convertida en dos useCases (uno por acción del usuario)
    //private val repository: CountriesRepository
    private val fetchAllCountriesUseCase: FetchAllCountriesUseCase,
    private val fetchCountriesByContUseCase: FetchCountriesByContUseCase
) : ViewModel() {

    // Flujo que concentra las acciones a las que debe estar atento este ViewModel para la UI
    // private val _uiAction = MutableSharedFlow<UiAction>()
    private val _state = MutableStateFlow(UiState())
    //val state: StateFlow<UiState> = _uiAction
    val state : StateFlow<UiState> = _state.asStateFlow()

    fun onUiAction(action: UiAction) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            when (action) {  // Discrimina en función de la acción solicitada (Totalidad o filtrado)
                is UiAction.LoadCountries -> {
                    fetchAllCountriesUseCase().collect { countries ->
                        _state.value = UiState(countries = countries) }
                }

                is UiAction.FilterCountries -> {
                    when(action.optSelected){
                        "All(asc)"  -> fetchAllCountriesUseCase().collect { countries ->
                            _state.value = UiState(countries = countries) }
                        "All(desc)" -> fetchAllCountriesUseCase().map { countries ->
                            _state.value = UiState(countries = countries.sortedByDescending { it.cname }) }
                        else -> fetchCountriesByContUseCase(action.optSelected).collect { countries ->
                            _state.value = UiState(countries = countries) }
                    }
                }
            }
        }
    }

    // Clase que sólo contiene estado y no realiza ninguna operación
    data class UiState(
        // Para albergar el estado que detecta si la pantalla está cargando
        val loading: Boolean = false,
        val countries: List<Country> = emptyList(),
        val netAvailable: Boolean = true
    )

    sealed class UiAction {
        data object LoadCountries : UiAction()
        data class FilterCountries(val optSelected: String) : UiAction()
    }
}