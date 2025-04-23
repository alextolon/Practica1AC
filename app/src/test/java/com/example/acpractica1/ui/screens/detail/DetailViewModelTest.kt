package com.example.acpractica1.ui.screens.detail

import app.cash.turbine.test
import com.example.acpractica1.data.CoroutinesTestRule
import com.example.acpractica1.sampleCountry
import com.example.acpractica1.ui.screens.detail.DetailViewModel.UiState
import com.example.acpractica1.ui.screens.home.HomeViewModel
import com.example.acpractica1.usecases.CambiaFriendlyUseCase
import com.example.acpractica1.usecases.FindCountryByNameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    // Gestiona la ejecución de corrutinas para estos tests
    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()
    // Property que simula una instancia de FindCountryByNameUseCase
    @Mock
    lateinit var findCountryByNameUseCase: FindCountryByNameUseCase
    // Property que simula una instancia de CambiaFriendlyUseCase
    @Mock
    lateinit var cambiaFriendlyUseCase: CambiaFriendlyUseCase
    // Property que simula una instancia de DetailViewModel
    private lateinit var vm : DetailViewModel
    // Como ahora sólo interviene un país
    private val country = sampleCountry("Argentina")
    // Se ejecuta antes de cada test solicitado
    @Before
    fun setUp() {
        // Genera una instancia simulada de DetailViewModel con datos y properties mockeados
        vm = DetailViewModel("Argentina", findCountryByNameUseCase, cambiaFriendlyUseCase)
    }

    @Test
    fun `UI updated with the country on start`(): Unit = runTest {
        // Simula el comportamiento de findCountryByNameUseCase
        whenever(findCountryByNameUseCase.invoke("Argentina")).thenReturn(flowOf(country))
        // Territorio turbine (test) (awaitItem())
        vm.state.test(timeout = 5.seconds) {
            assertEquals(UiState(), awaitItem()) // Comprueba el estado antes de tirar de VM
            // Ahora que tenemos LoadCountry, se le pasa el país de prueba
            vm.onAction(DetailAction.LoadCountry("Argentina"))
            // Se comprueba que el tiempo de carga existe
            assertEquals(UiState(loading = true), awaitItem())
            // Tras lo anterior se comprueba que ha cargado el país
            assertEquals(UiState(country = country), awaitItem())
        }
    }

    @Test
    fun `Gaymable is updated in local data source`() = runTest {
        val country = sampleCountry("Argentina")
        whenever(cambiaFriendlyUseCase.invoke(country))
        // Territorio turbine (test) (awaitItem())
        vm.state.test(timeout = 5.seconds) {
            assertEquals(UiState(), awaitItem()) // Comprueba el estado antes de tirar de VM
            vm.onAction(DetailAction.FriendlyClick)
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(country.copy(gaymable = true), awaitItem())
        }
    }
}