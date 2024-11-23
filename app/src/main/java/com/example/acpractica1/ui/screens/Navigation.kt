package com.example.acpractica1.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import com.example.acpractica1.ui.screens.detail.DetailScreen
import com.example.acpractica1.ui.screens.detail.DetailViewModel
import com.example.acpractica1.ui.screens.home.HomeScreen
import com.example.acpractica1.ui.screens.home.HomeViewModel

// Con esto etiquetamos las rutas para no tener que hardcodearlas en el código
sealed class NavScreen(val route: String) {
    data object Home : NavScreen("home")
    data object Detail : NavScreen("detail/{${NavArgs.CountryName.key}}")
    fun formaRuta(countryName: String) = "detail/${countryName}"
}
// Con esto etiquetamos los argumentos para no tener que hardcodearlos en el código
enum class NavArgs(val key: String) {
    CountryName("countryName")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val countriesRepository = CountriesRepository(remoteDataSource = CountriesRemoteDataSource())
    // Objeto principal que concentra los elementos de navegación
    NavHost(navController = navController , startDestination = NavScreen.Home.route) {
        // Objeto para establecer endpoint interno para pantalla principal
        composable(NavScreen.Home.route) {
            HomeScreen(
                onCountryClick = { country ->
                    navController.navigate(NavScreen.Detail.formaRuta(country.cname))
                },
                viewModel { HomeViewModel(countriesRepository) }
            )
        }

        // Objeto para establecer endpoint interno para pantalla detalle
        composable(NavScreen.Detail.route,
            arguments = listOf(
                navArgument(
                    NavArgs.CountryName.key)
                { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val countryArgName = requireNotNull(backStackEntry.arguments?.getString(NavArgs.CountryName.key))
            DetailScreen(
                viewModel { DetailViewModel(countryArgName, countriesRepository) },
                // Gestiona el botón <- para volver a la pantalla que llamó
                onBack = { navController.popBackStack() })
        }
    }
}