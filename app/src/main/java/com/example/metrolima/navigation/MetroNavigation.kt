package com.example.metrolima.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metrolima.R
import com.example.metrolima.presentation.screens.*

/* ---------------------------
   Definición de rutas
---------------------------- */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Stations : Screen("stations")
    object Routes : Screen("routes")
    object Settings : Screen("settings")
    object Favorites : Screen("favorites")
    object RouteDetail : Screen("route_detail/{origin}/{destination}") {
        fun createRoute(origin: String, destination: String) = "route_detail/$origin/$destination"
    }
    object StationDetail : Screen("station_detail/{stationId}") {
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }
}

/* ---------------------------
   Navegación principal
---------------------------- */
@Composable
fun MetroNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = {
                    navController.navigate(Screen.RouteDetail.createRoute("La Cultura", "Bayóvar"))
                },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        // Lista de estaciones
        composable(Screen.Stations.route) {
            StationsScreen(
                stations = listOf(
                    Station("1", "Estación La Cultura", "1", "San Borja", R.drawable.estacion1),
                    Station("2", "Estación Bayóvar", "1", "San Juan de Lurigancho", R.drawable.estacion2),
                    Station("3", "Estación Villa El Salvador", "1", "Villa El Salvador", R.drawable.estacion3),
                    Station("4", "Estación Los Jardines", "1", "San Juan de Lurigancho", R.drawable.estacion4),
                    Station("5", "Estación San Carlos", "1", "San Juan de Lurigancho", R.drawable.estacion5)
                ),
                onBack = { navController.popBackStack() },
                onStationClick = { station ->
                    navController.navigate(Screen.StationDetail.createRoute(station.id))
                }
            )
        }

        // Detalle de estación
        composable(Screen.StationDetail.route) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId") ?: ""
            val selectedStation = Station(
                id = stationId,
                name = "Estación Central",
                line = "1",
                district = "Cercado de Lima",
                imageRes = R.drawable.estacion1
            )
            StationDetailScreen(
                station = selectedStation,
                onBack = { navController.popBackStack() },
                onNavigateToRoute = { origin, destination ->
                    navController.navigate(Screen.RouteDetail.createRoute(origin, destination))
                }
            )
        }

        // Favoritos
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Detalle de ruta
        composable(Screen.RouteDetail.route) { backStackEntry ->
            val origin = backStackEntry.arguments?.getString("origin") ?: "Desconocido"
            val destination = backStackEntry.arguments?.getString("destination") ?: "Desconocido"
            RouteDetailScreen(
                origin = origin,
                destination = destination,
                onBack = { navController.popBackStack() },
                onSaveRoute = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        // Configuración
        composable(Screen.Settings.route) {
            PlaceholderScreen(
                title = "Configuración",
                onBack = { navController.popBackStack() }
            )
        }

        // Rutas
        composable(Screen.Routes.route) {
            PlaceholderScreen(
                title = "Rutas",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/* ---------------------------
   Pantalla genérica temporal
---------------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("$title - En desarrollo")
        }
    }
}