package com.example.acpractica1.ui.screens.detail

import app.cash.turbine.test
import com.example.acpractica1.data.CoroutinesTestRule
import com.example.acpractica1.data.buildCountriesRepositoryWith
import com.example.acpractica1.domain.Country
import com.example.acpractica1.sampleCountries
import com.example.acpractica1.sampleCountry
import com.example.acpractica1.ui.screens.home.HomeViewModel
import com.example.acpractica1.usecases.CambiaFriendlyUseCase
import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import com.example.acpractica1.usecases.FindCountryByNameUseCase
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class DetailIntegrationsTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val countriesRepository = buildCountriesRepositoryWith(localData = sampleCountries("Argentina", "Spain"))
        vm = DetailViewModel("Argentina", FindCountryByNameUseCase(countriesRepository), CambiaFriendlyUseCase(countriesRepository))
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test(timeout = 5.seconds) {
            assertEquals(DetailViewModel.UiState(), awaitItem())
            vm.onAction(DetailAction.LoadCountry("Argentina"))
            assertEquals(DetailViewModel.UiState(loading = true), awaitItem())
            assertEquals(DetailViewModel.UiState(country = sampleCountry("Argentina")), awaitItem())
        }
    }

    @Test
    fun `Gaymable is updated in local data source`() = runTest {
        vm.state.test(timeout = 5.seconds) {
            assertEquals(DetailViewModel.UiState(), awaitItem())
            vm.onAction(DetailAction.FriendlyClick)
            //runCurrent()
            assertEquals(DetailViewModel.UiState(loading = true), awaitItem())
            assertEquals(DetailViewModel.UiState(country = sampleCountry("Argentina").copy(gaymable = true)), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    // Atento el buildViewModel que debería construirse es el del DetailViewModel
    private fun buildViewModelWith(
        localData: List<Country> = emptyList(),
        remoteData: List<Country> = emptyList()
    ): HomeViewModel {
        val fetchAllCountriesUseCase = FetchAllCountriesUseCase(buildCountriesRepositoryWith(localData, remoteData))
        val fetchCountriesByContUseCase = FetchCountriesByContUseCase(buildCountriesRepositoryWith(localData, remoteData))
        return HomeViewModel(fetchAllCountriesUseCase, fetchCountriesByContUseCase)
    }
}