package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FriendlyClick: DetailAction
    data object MuestraMens: DetailAction
}

class DetailViewModel(private val name: String) : ViewModel() {

    // Property en la que se instancia un objeto de tipo CountriesRepository
    private val repository: CountriesRepository = CountriesRepository()
    // Property que recoge el estado de la UI
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()
    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null,
        val mesnack: String? = null
    )

    // Para la generación de un channel hace falta un evento propio de UI
    /*sealed interface UIEvent {
        data class MuestraMensaje(val mensaje: String) : UIEvent
    }*/

    /*private val _events = Channel<UIEvent>()
    val events: Flow<UIEvent> = _events.receiveAsFlow()*/

    // Constructor que establece los valores del estado
    // al tirar del viewModel de la pantalla de detalle
    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, country = repository.findCountryByName(name))
        }
    }

    fun onAction(action: DetailAction) {
        when(action) {
            is DetailAction.FriendlyClick -> onFriendlyClick()
            is DetailAction.MuestraMens -> onMuestraMens()
        }
    }

    private fun onFriendlyClick() {
        //_events.trySend(UIEvent.MuestraMensaje("País aceptable"))
        _state.update { it.copy(mesnack = "País aceptable") }
    }

    private fun onMuestraMens() {
        _state.update { it.copy(mesnack = null) }
    }
}
