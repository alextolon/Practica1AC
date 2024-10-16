package com.example.acpractica1.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.acpractica1.ui.screens.detail.DetailScreen
import com.example.acpractica1.ui.screens.detail.DetailViewModel
import com.example.acpractica1.ui.screens.home.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    // Objeto principal que concentra los elementos de navegación
    NavHost(navController = navController , startDestination = "home") {
        // Objeto para establecer endpoint interno para pantalla principal
        composable("home") {
            HomeScreen(onCountryClick = { country ->
                navController.navigate("detail/${country.cname}")
            })
        }
        // Objeto para establecer endpoint interno para pantalla detalle
        composable("detail/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val countryArgName = requireNotNull(backStackEntry.arguments?.getString("cname"))
            DetailScreen(
                viewModel { DetailViewModel(countryArgName) },
                // Gestiona el botón <- para volver a la pantalla principal
                onBack = { navController.popBackStack() })
        }
    }
}