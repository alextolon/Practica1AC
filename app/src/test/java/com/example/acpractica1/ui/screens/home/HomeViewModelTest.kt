package com.example.acpractica1.ui.screens.home

import app.cash.turbine.test
import com.example.acpractica1.data.CoroutinesTestRule
import com.example.acpractica1.sampleCountries
import com.example.acpractica1.ui.screens.home.HomeViewModel.UiState
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    // Gestiona la ejecución de corrutinas para estos tests
    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()
    // Property que simula una instancia de FetchAllCountriesUseCase
    @Mock
    lateinit var fetchAllCountriesUseCase: FetchAllCountriesUseCase
    // Property que simula una instancia de FetchCountriesByContUseCase
    @Mock
    lateinit var fetchCountriesByContUseCase: FetchCountriesByContUseCase
    // Property que simula una instancia de HomeViewModel
    private lateinit var vm : HomeViewModel
    // Se ejecuta antes de cada test solicitado
    @Before
    fun setUp() {
        // Genera una instancia simulada de HomeViewModel con properties mockeados
        vm = HomeViewModel(fetchAllCountriesUseCase, fetchCountriesByContUseCase)
    }

    @Test
    fun `Countries not requested if UI not ready`() = runTest {
        vm.state.first()
        runCurrent()

        verify(fetchAllCountriesUseCase, times(0)).invoke()
    }

    @Test
    fun `Countries requested if UI ready`(): Unit = runTest {
        val countries = sampleCountries("Argentina", "Spain")
        whenever(fetchAllCountriesUseCase.invoke()).thenReturn(flowOf(countries))

        // Territorio turbine (test) (awaitItem())
        vm.state.test(timeout = 5.seconds) {
            assertEquals(UiState(), awaitItem()) // Comprueba el estado antes de tirar de VM
            vm.onUiAction(HomeViewModel.UiAction.LoadCountries)
            assertEquals(UiState(loading = true), awaitItem())   // Ahora verifica
            assertEquals(UiState(countries = countries), awaitItem())
            cancelAndIgnoreRemainingEvents()  // Comentar para ver qué pasa
        }
    }

    @Test
    fun `Countries selected by continent requested if UI ready`(): Unit = runTest {
        val countries = sampleCountries("Argentina", "Uruguay")
        whenever(fetchCountriesByContUseCase.invoke(optSelected = "South America")).thenReturn(flowOf(countries))

        vm.state.test(timeout = 5.seconds) {
            assertEquals(UiState(), awaitItem()) // Comprueba el estado antes de tirar de VM
            vm.onUiAction(HomeViewModel.UiAction.FilterCountries(optSelected = "South America"))
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(countries = countries), awaitItem())
            cancelAndIgnoreRemainingEvents()  // Comentar para ver qué pasa
        }
    }
}