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
import com.example.acpractica1.usecases.FetchCountriesByContUseCase

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
        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = false), awaitItem())
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