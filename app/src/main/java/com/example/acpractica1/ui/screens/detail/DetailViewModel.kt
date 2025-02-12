package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.usecases.CambiaFriendlyUseCase
import com.example.acpractica1.usecases.FindCountryByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailAction {
    data object FriendlyClick: DetailAction
}

/* Antes class DetailViewModel(
    name: String,*/
@HiltViewModel
class DetailViewModel @Inject constructor(
    @CountryName name: String,
    // Property en la que se instanciaba un objeto de tipo CountriesRepository
    // sustituida por useCase para cada acción correspondiente
    findCountryByNameUseCase: FindCountryByNameUseCase,
    private val cambiaFriendlyUseCase: CambiaFriendlyUseCase
) : ViewModel() {


    //private val repository: CountriesRepository = CountriesRepository()
    // Property que recoge el estado de la UI
    //private val _state = MutableStateFlow(UiState())
    //val state: StateFlow<UiState> get() = _state.asStateFlow()
    val state: StateFlow<UiState> = findCountryByNameUseCase(name)
        .map { country -> UiState(country = country) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )
    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )

    // Función para concentrar las acciones a las que debe estar atento este ViewModel
    fun onAction(action: DetailAction) {
        when(action) {
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
}
