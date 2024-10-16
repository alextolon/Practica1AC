package com.example.acpractica1.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.launch

class DetailViewModel(private val name: String) : ViewModel() {

    // Property en la que se instancia un objeto de tipo CountriesRepository
    private val repository: CountriesRepository = CountriesRepository()
    // Property que recoge el estado de la UI
    var state by mutableStateOf(UiState())
        private set
    // Data class para almacenar los datos del estado
    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )
    // Constructor que establece los valores del estado
    // al tirar del viewModel de la pantalla de detalle
    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, country = repository.findCountryByName(name))
        }
    }
}
