package com.example.acpractica1.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.launch

class DetailViewModel(private val id: String) : ViewModel() {

    private val repository: CountriesRepository = CountriesRepository()

    var state by mutableStateOf(UiState())
        private set

    data class UiState(
        val loading: Boolean = false,
        val country: Country? = null
    )

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, country = repository.findCountryByCode(id))
        }
    }
}
