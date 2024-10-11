package com.example.acpractica1.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.acpractica1.data.Country
import com.example.acpractica1.R
import com.example.acpractica1.ui.screens.home.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Esta función construye la pantalla Detail de la aplicación de la mano de un
// ViewModel que contiene el estado de la pantalla
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {
    // Para no trabajar directamente con el estado que proporciona el ViewModel
    // y poder llamarlo de una manera más simple
    val state = vm.state
    Screen { // Objeto Compose para construir la pantalla
        Scaffold(  // Objeto Compose de layout para crear interfaces con Material
                   // Design que ubica componentes predefinidos sobre un contenedor.
            topBar = {
                TopAppBar( // Objeto Compose para construir la barra principal de la pantalla
                    title = { Text(text = state.country?.cname ?: "") },
                    navigationIcon = {
                        IconButton(onClick = onBack ) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back))
                        }
                    }
                )
            }
        ) { padding ->
            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.country?.let { country ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = country.cflag,
                        contentDescription = country.cflag,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                    )
                    Text(
                        text = country.cname,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium

                    )
                }
            }
        }
    }
}