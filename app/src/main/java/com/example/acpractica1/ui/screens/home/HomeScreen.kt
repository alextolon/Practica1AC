package com.example.acpractica1.ui.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.acpractica1.ui.theme.ACPractica1Theme
import com.example.acpractica1.R
import com.example.acpractica1.domain.Country
import com.example.acpractica1.ui.theme.GreenTAB
import com.example.acpractica1.ui.theme.Pink60
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

enum class NetworkType{
    NoDisponible, Wifi, RedMovil, Ethernet
}

private fun verifConnect(context: Context): NetworkType {

    // Se construye un gestor de conectividad
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Se instancia un objeto para obtener una referencia a la red predeterminada actual
    val network = connectivityManager.activeNetwork ?: return NetworkType.NoDisponible

    // Ahora se pueden utilizar las "capabilities" de la red activa a partir del objeto anterior
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.NoDisponible

    return when {
        // Indica que la conectividad va por WiFi
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.Wifi

        // Indica que la conectividad va por redes móviles
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) && activeNetwork.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.RedMovil

        // Indica que la conectividad va por redes móviles
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.Ethernet

        else -> NetworkType.NoDisponible
    }
}

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

// Sobrecargar la fun HomeScreen para desmembrarla de modo que
// la primera se coma las dependencias,

@Composable
fun HomeScreen(
    onCountryClick: (Country) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    // Necesito recoger el estado y almacenarlo
    //vm.onUiAction(HomeViewModel.UiAction.LoadCountries)

    // Ahora se llama a la función simplificada para test
    // Lanzamiento de la corrutina que vigila los cambios de estado de la UI
    val coroutineScope = rememberCoroutineScope()
    val activity = (LocalContext.current as Activity)
    if (verifConnect(activity) != NetworkType.NoDisponible) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                //vm.onUiReady()
                vm.onUiAction(HomeViewModel.UiAction.LoadCountries)
            }
        }
    } else {
        // ConnectDialog composable
        ConnectDialog(
            message = "No hay redes disponibles",
            /*startActivity(enableWifiIntent)*/  //enableWifi(this)
        )
    }
    val state by vm.state.collectAsState()
    HomeScreen(
        onCountryClick = onCountryClick,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCountryClick: (Country) -> Unit,
    state: StateFlow<HomeViewModel.UiState>
    //vm: HomeViewModel = hiltViewModel()
) {
    val homeState = rememberHomeState()
    /* Lanzamiento de la corrutina que vigila los cambios de estado de la UI
    val coroutineScope = rememberCoroutineScope()
    val activity = (LocalContext.current as Activity)
    if (verifConnect(activity) != NetworkType.NoDisponible) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                //vm.onUiReady()
                vm.onUiAction(HomeViewModel.UiAction.LoadCountries)
            }
        }
    } else {
        // ConnectDialog composable
        ConnectDialog(
            message = "No hay redes disponibles",
            /*startActivity(enableWifiIntent)*/  //enableWifi(this)
        )
    }*/

    Screen {
        Scaffold(
            state = state,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = GreenTAB,
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
                            //vm.onMenuSelected(continentSelected)
                            vm.onUiAction(HomeViewModel.UiAction.FilterCountries(continentSelected))
                        }
                    },
                    scrollBehavior = homeState.scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection)
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

@Suppress("DEPRECATION")
@SuppressLint("ContextCastToActivity")
@Composable
private fun ConnectDialog(
    message: String,
    modifier: Modifier = Modifier
) {
    // Propiedad que permite utilizar el método finish()
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = "¡Atención!") },
        text = { Text(text = message) },
        modifier = modifier,
        dismissButton = {
            // Botón para cerrar la app
            TextButton(
                onClick = {
                    activity.finish()
                    exitProcess(0)
                }
            ) {
                Text(text = "Salir")
            }
        },
        confirmButton = {
            // Botón para habilitar la Wifi
            TextButton (onClick = {
                val enableWifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
                //val mIntent = getIntent()
                //activity.startActivity(enableWifiIntent)
                // Para que vuelva del menu configuración Wifi
                //activity.startActivity(makeMainActivity(i))
            }) {
                Text(text = "Conecta Wifi")
            }
        }
    )
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
        Box {
            AsyncImage(
                model = country.cflag,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            if(country.gaymable) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Pink60,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                        .size(32.dp)
                )
            }
        }

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
                    text = stringResource(R.string.etiqnombre),
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
                    text = stringResource(R.string.etiqcapital),
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
                    text = stringResource(R.string.etiqcontinente),
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