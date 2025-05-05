package com.example.acpractica1

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.acpractica1.sampleCountries
import com.example.acpractica1.ui.screens.home.HomeScreen
import com.example.acpractica1.ui.screens.home.HomeViewModel
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoading_showProgress(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = true),
                onFilterCountries = {}
            )
        }
        onNodeWithTag("LoadingOK").assertExists()
    }

    @Test
    fun whenAllOk_moviesAreShown(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading= false, countries = sampleCountries("Argentina", "Spain", "France")),
                onFilterCountries = {}  // Aquí sería algo como un action.LoadCountries
            )
        }
        onNodeWithText("Argentina").assertExists()
    }

    /*
    @Test
    fun whenContinentOk_moviesAreShown(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading= false, countries = sampleCountries("Argentina", "Uruguay", "Peru")),
                onFilterCountries = { continentSelected ->   }  // Aquí sería algo como un action.FilterCountries("South America")
            )
        }
        onNodeWithText("Argentina").assertExists()
    }*/
}