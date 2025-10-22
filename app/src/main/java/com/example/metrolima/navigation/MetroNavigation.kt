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
    object About : Screen("about")
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
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        // Lista de estaciones - USA ROOM
        composable(Screen.Stations.route) {
            StationsScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onStationClick = { stationId ->
                    navController.navigate(Screen.StationDetail.createRoute(stationId.toString()))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Detalle de estación - USA ROOM
        composable(Screen.StationDetail.route) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId")?.toIntOrNull() ?: 1
            StationDetailScreen(
                stationId = stationId,
                onBack = { navController.popBackStack() },
                onNavigateToRoute = { origin, destination ->
                    navController.navigate(Screen.RouteDetail.createRoute(origin, destination))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
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


        // Configuración
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToAbout = { navController.navigate(Screen.About.route) }
            )
        }

        // Acerca de
        composable(Screen.About.route) {
            AboutScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }


        // Rutas - Pantalla de selección de rutas
        composable(Screen.Routes.route) {
            RouteSelectionScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { /* Ya estamos en rutas */ },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
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