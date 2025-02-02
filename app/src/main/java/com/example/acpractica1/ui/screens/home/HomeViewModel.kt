package com.example.acpractica1.ui.screens.home

// Architecture components (lifecycle)
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Forma de generar viewmodel heredando de ViewModel
class HomeViewModel(
    // Property que enlaza objeto de tipo CountriesRepository
    // que contenía los métodos fecthAllCountries() y fetchCountriesByCont()
    // convertida en dos useCases (uno por acción del usuario)
    //private val repository: CountriesRepository
    private val fetchAllCountriesUseCase: FetchAllCountriesUseCase,
    private val fetchCountriesByContUseCase: FetchCountriesByContUseCase
) : ViewModel() {

    // Flujo que concentra las acciones a las que debe estar atento este ViewModel para la UI
    private val _uiAction = MutableSharedFlow<UiAction>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<UiState> = _uiAction
        .flatMapLatest { action ->
            when (action) {  // Discrimina en función de la acción solicitada (Totalidad o filtrado)
                is UiAction.LoadCountries -> {
                        fetchAllCountriesUseCase().map { UiState(countries = it) }
                }


                is UiAction.FilterCountries -> {
                    when(action.optSelected){
                        "All(asc)"  -> fetchAllCountriesUseCase().map { UiState(countries = it) }
                        "All(desc)" -> fetchAllCountriesUseCase().map { it -> UiState(countries = it.sortedByDescending { it.cname }) }
                        else -> fetchCountriesByContUseCase(action.optSelected).map { UiState(countries = it) }
                    }
                }
                /* null -> TODO() */
            }
        }
        .stateIn(  // Así se convierte a estado
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    fun onUiAction(action: UiAction) {
        viewModelScope.launch {
            _uiAction.emit(action)
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

