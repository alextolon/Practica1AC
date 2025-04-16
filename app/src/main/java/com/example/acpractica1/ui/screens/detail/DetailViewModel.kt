package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.ui.screens.home.HomeViewModel.UiAction
import com.example.acpractica1.ui.screens.home.HomeViewModel.UiState
import com.example.acpractica1.usecases.CambiaFriendlyUseCase
import com.example.acpractica1.usecases.FindCountryByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    //_state.value = findCountryByNameUseCase(name).map { country -> UiState(country = country) }
    val state: StateFlow<UiState> = _state.asStateFlow()
    //val myUiState = UiState(loading = false, country = Country("USA"))
    //val countryFlow1: Flow<Country?> = myUiState1.countryToFlow()
    /*init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                country = findCountryByNameUseCase(name).map { country -> UiState(country = country) })
        }
    }*/

    // Función para concentrar las acciones a las que debe estar atento este ViewModel
    fun onAction(action: DetailAction) {
            _state.value = UiState(loading = true)
            when (action) {
                is DetailAction.LoadCountry -> {
                    viewModelScope.launch {
                        findCountryByNameUseCase(action.countryName).collect {
                            _state.value = UiState(country = it)
                        }
                    }
                }
                is DetailAction.FriendlyClick -> onFriendlyClick()
            }
    }

    private fun onFriendlyClick() {
        // Ahora cambiar esto
        state.value.country?.let {
            viewModelScope.launch {
                cambiaFriendlyUseCase(it)
            }
        }
    }
    /*val state: StateFlow<UiState> = findCountryByNameUseCase(name)
        .map { country -> UiState(country = country) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )*/
    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )
}
