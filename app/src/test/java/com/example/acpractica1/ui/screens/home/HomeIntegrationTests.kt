package com.example.acpractica1.ui.screens.home

import com.example.acpractica1.sampleCountries
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.data.CoroutinesTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import app.cash.turbine.test
import com.example.acpractica1.data.buildCountriesRepositoryWith
import com.example.acpractica1.domain.Country
import com.example.acpractica1.ui.screens.home.HomeViewModel.UiState
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import kotlin.time.Duration.Companion.seconds

class HomeIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = sampleCountries("Argentina", "Spain")
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )
        vm.onUiAction(HomeViewModel.UiAction.LoadCountries)
        // Territorio turbine (test) (awaitItem())
        // Probar con ¿saveCountries?
        vm.state.test(timeout = 5.seconds) {
            //assertEquals(UiState(), awaitItem()) // Comprueba el estado antes de tirar de VM
            //assertEquals(UiState(loading = true), awaitItem())
            assertEquals(emptyList<Country>(), awaitItem())
            assertEquals(remoteData, awaitItem())
        }
    }
}

private fun buildViewModelWith(
    localData: List<Country> = emptyList(),
    remoteData: List<Country> = emptyList()
): HomeViewModel {
    val fetchAllCountriesUseCase = FetchAllCountriesUseCase(buildCountriesRepositoryWith(localData, remoteData))
    val fetchCountriesByContUseCase = FetchCountriesByContUseCase(buildCountriesRepositoryWith(localData, remoteData))
    return HomeViewModel(fetchAllCountriesUseCase, fetchCountriesByContUseCase)
}