package com.example.acpractica1.ui.screens.home

// Architecture components (lifecycle)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acpractica1.domain.Country
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
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
    private val uiReady = MutableStateFlow(false)
    // Lo mismo para continentes
    //private val uiReadyCont = MutableStateFlow("")
    // Property que mantiene info variable con formato UIState
    // Sólo puede modificarse desde dentro de esta clase
    private val _state =  MutableStateFlow(UiState())
    //val state: StateFlow<UiState> get() = _state.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<UiState> = uiReady
        // Con esto aguanta hasta que la UI está lista (uiReady a true)
        .filter { it }
        // Ahora ya tira de los países del useCase que le corresponde
        .flatMapLatest { fetchAllCountriesUseCase() }
        // convirtiéndolo en componente UIState
        .map { UiState(countries = it) }
        // para finalmente convertir el Flow en un StateFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )
    /*@OptIn(ExperimentalCoroutinesApi::class)
    val stateCont: StateFlow<UiState> = uiReady
        // Con esto selecciona en función del continente)
        .filter { it }
        // Ahora ya tira de los países del repositorio
        .flatMapLatest { repository.fetchCountriesByCont(continent)  }
        // convirtiéndolo en componente UIState
        .map { UiState(countries = it) }
        // para finalmente convertir el Flow en un StateFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )*/
    // Simula que primero está cargando (Circulito) y luego  muestra información
    // Por defecto, muestra todos los países. Si elige continente los de esa elección.
    fun onUiReady() {
        uiReady.value = true
        /*viewModelScope.launch {
            _state.value = UiState(loading = true)
            // Recolecta datos y con ellos modifica el estado
            repository.countries.collect { countries ->
                _state.value = UiState(loading = false, countries = countries)
            }
        }*/
    }

    fun onMenuSelected(optSelected: String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            fetchCountriesByContUseCase(optSelected).collect { countries ->
                _state.value = UiState(
                    loading = false,
                    countries =
                    when (optSelected) {
                        "All(asc)" -> countries
                        "All(desc)" -> countries
                            .sortedByDescending { it.cname }
                        else -> countries
                    }
                )
            }
        }
    }

    // Clase que sólo contiene estado y no realiza ninguna operación
    data class UiState(
        // Para albergar el estado que detecta si la pantalla está cargando
        val loading: Boolean = false,
        val countries: List<Country> = emptyList()
    )
}