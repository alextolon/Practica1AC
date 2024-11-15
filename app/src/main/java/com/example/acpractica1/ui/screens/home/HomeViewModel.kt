package com.example.acpractica1.ui.screens.home

// Architecture components (lifecycle)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.Country
import com.example.acpractica1.data.CountriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Forma de generar viewmodel heredando de ViewModel
class HomeViewModel : ViewModel() {
    // Property que mantiene info variable con formato UIState
    // Sólo puede modificarse desde dentro de esta clase
    private val _state =  MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    // Property que enlaza objeto de tipo CountriesRepository
    // que contiene los métodos fecthAllCountries() y fetchCountriesByCont()
    val repository = CountriesRepository()

    // Simula que primero está cargando (Circulito) y luego  muestra información
    // Por defecto, muestra todos los países. Si elige continente los de esa elección.
    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                countries = repository.fetchAllCountries()
            )
        }
    }

    fun onMenuSelected(optSelected: String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                countries =
                when (optSelected) {
                    "All(asc)" -> repository.fetchAllCountries()
                    "All(desc)" -> repository.fetchAllCountries()
                        .sortedByDescending { it.cname }
                    else -> repository.fetchCountriesByCont(optSelected)
                }
            )
        }
    }

    // Clase que sólo contiene estado y no realiza ninguna operación
    data class UiState(
        // Para albergar el estado que detecta si la pantalla está cargando
        val loading: Boolean = false,
        val countries: List<Country> = emptyList()
    )
}