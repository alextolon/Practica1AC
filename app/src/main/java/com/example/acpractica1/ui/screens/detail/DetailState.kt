package com.example.acpractica1.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
// Los estados, antes en el ViewModel, se concentran en esta clase
class DetailState(
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState
) {
    // Así como la función que muestra el Snackbar
    @Composable
    fun MuesnackMensEffect(mensnack: String?, onMuestraMens: () -> Unit) {
        LaunchedEffect(mensnack) {
            mensnack?.let {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(it)
                onMuestraMens()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    // Para preparar la toolbar para que cambie color al hacer scroll
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): DetailState {
    return remember { DetailState(scrollBehavior, snackbarHostState) }
}