package com.example.acpractica1.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    // Lanzamiento de la corrutina que vigila los cambios de estado de la UI
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            vm.onUiReady()
        }
    }

    Screen {
        // Para preparar la toolbar para que cambie color al hacer scroll (1)
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF03583f),
                        //MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.planet),
                                contentDescription = "Globo",
                                tint = Color.Unspecified
                            )
                        }
                    },
                    title = {
                        Text(stringResource(id = R.string.app_name),
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    },
                    actions = {
                        TopAppBarDropdownMenu { continentSelected ->
                            vm.onMenuSelected(continentSelected)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->

            // Al utilizar StateFlow en HomeViewModel (estructura Kotlin pero no de Compose)
            // hay que transformarlo ahora en un estado de Compose
            val state by vm.state.collectAsState()
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
            LazyColumn(
                contentPadding = padding //Evita que TopBar coma zona de contenido
            ) {
                // Aquí generas cada item de lista a partir
                items(state.countries) {
                    CountryItem(country = it) { onCountryClick(it) }
                }
            }
        }
    }
}

@Composable
fun TopAppBarDropdownMenu(
    onContinentChange: (String) -> Unit
) {
    val menuDesplegado = remember { mutableStateOf(false) }
    val menuContinentes =
        listOf("All(asc)", "All(desc)", "Europe", "North America", "South America", "Asia", "The Caribean", "Africa", "Australia", "Central America", "Oceana")
    // Para que el icono cambie al estar o no desplegado el menú
    val icon = if (menuDesplegado.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            menuDesplegado.value = true
        }) {
            Icon(
                icon,
                modifier = Modifier.fillMaxSize(1.0F),
                contentDescription = "Menú Continentes",
                tint = Color.White
            )
        }
    }

    DropdownMenu(
        expanded = menuDesplegado.value,
        onDismissRequest = { menuDesplegado.value = false },
    ) {
        // Integra en una sola lambda todos los MenuItem del desplegable
        menuContinentes.forEach { opcion ->
            DropdownMenuItem(
                text = { Text(opcion,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )},
                onClick = {
                    onContinentChange(opcion)
                    menuDesplegado.value = false
                },
            )
        }
    }
}

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