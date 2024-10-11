package com.example.acpractica1.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.acpractica1.ui.theme.ACPractica1Theme
import com.example.acpractica1.data.Country
import com.example.acpractica1.R
import kotlinx.coroutines.launch

@Composable
// Función que construye la pantalla principal
fun Screen(content: @Composable () -> Unit) {
    ACPractica1Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCountryClick: (Country) -> Unit,
    vm: HomeViewModel = viewModel { HomeViewModel() }
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            vm.onUiReady()
        }
    }

   /* val ctx = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        coroutineScope.launch {
            val region = if (granted) ctx.getRegion() else "US"
            vm.onUiReady(region)
        }
    }*/

    Screen {
        // Para preparar la toolbar para que cambie color al hacer scroll (1)
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id=R.string.app_name)) },
                    // (2)
                    scrollBehavior = scrollBehavior
                )
            },
            //(3)
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->

            val state = vm.state
            // Controla lo que se muestra si está cargando o no
            if (state.loading) {
                // En caso de estar cargando, muestra el circulito
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // Cuando deje de cargar, muestra la lista
            LazyColumn {
                // Aquí generas cada item de lista
                items(state.countries, key = { it.ccode }) {
                    CountryItem(country = it) { onCountryClick(it) }
                }
               /* items(movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onClick = { onClick(movie)} )
                }*/
            }
        }
   }
 }


@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ){
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center, // Centrado horizontal
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = country.cflag,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clip(MaterialTheme.shapes.small)
        )
        Column {
            Text(
                text = country.cname,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier
                    .padding(2.dp)
            )
            Text(
                text = country.ccapital,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier
                    .padding(2.dp)
            )
            Text(
                text = country.ccontinent,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier
                    .padding(2.dp)
            )
        }
    }

    }
}