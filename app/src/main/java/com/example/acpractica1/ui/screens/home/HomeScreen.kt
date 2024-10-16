package com.example.acpractica1.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.acpractica1.ui.theme.ACPractica1Theme
import com.example.acpractica1.data.Country
import com.example.acpractica1.R
import com.example.acpractica1.ui.theme.GreenTAB
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
                    title = {
                        Box(modifier = Modifier
                            .fillMaxSize(),
                            //.background(Color(0xFF03583f)),
                            contentAlignment = Alignment.Center)
                        {
                            Text(stringResource(id = R.string.app_name),
                                fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.White)
                        }
                    },
                    // Esto soluciona el efecto "start padding" del
                    // background comentado siete líneas antes
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF03583f)
                    ),
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
                // Aquí generas cada item de lista a partir
                items(state.countries) {
                    CountryItem(country = it) { onCountryClick(it) }
                }
            }
        }
    }
}

//Seguir https://www.youtube.com/watch?v=x8oLAW78T5k
// Para componer la pantalla
@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFe7fff8))
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = country.cflag,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nombre:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
                Text(
                    text = country.cname,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Capital:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
                Text(
                    text = country.ccapital,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Continente:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
                Text(
                    text = country.ccontinent,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }
       }
   }
}