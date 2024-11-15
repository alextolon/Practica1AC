package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val name: String) : ViewModel() {

    // Property en la que se instancia un objeto de tipo CountriesRepository
    private val repository: CountriesRepository = CountriesRepository()
    // Property que recoge el estado de la UI
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()
    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )
    // Constructor que establece los valores del estado
    // al tirar del viewModel de la pantalla de detalle
    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, country = repository.findCountryByName(name))
        }
    }
}
