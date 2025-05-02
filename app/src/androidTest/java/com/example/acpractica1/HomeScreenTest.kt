package com.example.acpractica1

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                onCountryClick = {},
                state = UiState(loading = true)
            )
        }

        onNodeWithTag(//LOADING_INDICATOR_TAG).assertExists()
    }

}