package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.usecases.CambiaFriendlyUseCase
import com.example.acpractica1.usecases.FindCountryByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailAction {
    data class LoadCountry(val countryName: String): DetailAction
    data object FriendlyClick: DetailAction
}

/* Antes class DetailViewModel(
    name: String,*/
@HiltViewModel
class DetailViewModel @Inject constructor(
    @CountryName name: String,
    // Property en la que se instanciaba un objeto de tipo CountriesRepository
    // sustituida por useCase para cada acción correspondiente
    private val findCountryByNameUseCase: FindCountryByNameUseCase,
    private val cambiaFriendlyUseCase: CambiaFriendlyUseCase
) : ViewModel() {

    // Property que recoge el estado de la UI
    private val _state = MutableStateFlow(UiState())
    //
    val state: StateFlow<UiState> = _state.asStateFlow()

    // Función para concentrar las acciones a las que debe estar atento este ViewModel
    fun onAction(action: DetailAction) {
        viewModelScope.launch {
            when (action) {
                is DetailAction.LoadCountry -> {
                    _state.value = UiState(loading = true)
                    findCountryByNameUseCase(action.countryName).collect { country ->
                        _state.value = UiState(country = country)
                    }
                }
                is DetailAction.FriendlyClick -> {
                    val currentCountry = _state.value.country
                    if(currentCountry != null) {
                        _state.value = UiState(loading = true)
                        cambiaFriendlyUseCase(currentCountry)
                        findCountryByNameUseCase(currentCountry.cname)
                            .collect { updatedCountry ->
                                _state.value = UiState(country = updatedCountry)
                            }
                        //_state.value = UiState(country = currentCountry.copy(gaymable = true))
                    }
                }
            }
        }
    }

    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )
}
