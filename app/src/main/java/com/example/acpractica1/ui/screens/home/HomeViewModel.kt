package com.example.acpractica1.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
// Architecture components (lifecycle)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.data.Country
import com.example.acpractica1.data.CountriesRepository
import kotlinx.coroutines.launch

// Forma de generar viewmodel heredando de ViewModel
class HomeViewModel : ViewModel() {
    // Property que mantiene info variable con formato UIState
    // Sólo puede modificarse desde dentro de esta clase
    var state by mutableStateOf(UiState())
        private set
    // Property que enlaza objeto de tipo CountriesRepository
    // que contiene los métodos fecthAllCountries() y fetchCountriesByCont()
    val repository = CountriesRepository()

    // Simula que primero está cargando (Circulito) y luego  muestra información
    // Por defecto, muestra todos los países. Si elige continente los de esa elección.
    fun onUiReady(contElegido: MutableState<String>) {
        viewModelScope.launch {
            state = UiState(loading = true)
            // Si no detecta elección de continente
            if(contElegido.value == "")
                state = UiState(loading = false, countries = repository.fetchAllCountries())
            // Si detecta elección de continente
            else
                state = UiState(loading = false, countries = repository.fetchCountriesByCont(contElegido))
            //Si se coloca un punto de ruptura en la línea anterior y se ejecuta en modo debug
            //Se puede seleccionar repository.fetchAllCountries() para saber si coge datos de API
        }
    }
    // Clase que sólo contiene estado y no realiza ninguna operación
    data class UiState(
        // Para albergar el estado que detecta si la pantalla está cargando
        val loading: Boolean = false,
        val countries: List<Country> = emptyList()
    )
}