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
        // Genera estado con la acción en el HomeViewModel
        vm.onUiAction(HomeViewModel.UiAction.LoadCountries)
        // Territorio turbine (test) (awaitItem())
        vm.state.test(timeout = 5.seconds) {
            // Lo que hacemos es testear en secuencia los cambios de estado a testear
            // En coherencia con la inicialización en el HomeViewModel
            assertEquals(UiState(), awaitItem())
            // Dentro del action lo primero que hacía era poner loading a true
            assertEquals(UiState(loading = true, countries = emptyList()), awaitItem())
            // Rematando en LoadCountries un estado de countries = countries
            assertEquals(UiState(loading = false, countries = remoteData), awaitItem())
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = sampleCountries("Argentina", "Spain")
        val vm = buildViewModelWith(localData = localData)

        vm.onUiAction(HomeViewModel.UiAction.LoadCountries)

        vm.state.test(timeout = 5.seconds) {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true, countries = emptyList()), awaitItem())
            assertEquals(UiState(loading = false, countries = localData), awaitItem())
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