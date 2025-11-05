package com.example.metrolima.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metrolima.presentation.screens.*
import com.example.metrolima.presentation.viewmodel.LanguageViewModel

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
    object Lines : Screen("lines")

    object RouteDetail : Screen("route_detail/{origin}/{destination}") {
        fun createRoute(origin: String, destination: String) = "route_detail/$origin/$destination"
    }

    // ✅ Nueva ruta para preseleccionar origen y destino
    object RouteSelectionWithParams : Screen("routes/{origin}/{destination}") {
        fun createRoute(origin: String, destination: String): String {
            return "routes/${Uri.encode(origin)}/${Uri.encode(destination)}"
        }
    }

    object StationDetail : Screen("station_detail/{stationId}") {
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }

    object LineDetail : Screen("line_detail/{lineId}") {
        fun createRoute(lineId: Int) = "line_detail/$lineId"
    }
}

/* ---------------------------
   Navegación principal
---------------------------- */
@Composable
fun MetroNavigation() {
    val navController = rememberNavController()
    val languageViewModel: LanguageViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // 🏠 Pantalla de inicio
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToLines = { navController.navigate(Screen.Lines.route) },
                languageViewModel = languageViewModel
            )
        }

        // 🚉 Lista de Líneas
        composable(Screen.Lines.route) {
            LinesScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onLineClick = { lineId ->
                    navController.navigate(Screen.LineDetail.createRoute(lineId))
                },
                onBack = { navController.popBackStack() },
                languageViewModel = languageViewModel
            )
        }

        // 🧭 Detalle de línea
        composable(Screen.LineDetail.route) { backStackEntry ->
            val lineId = backStackEntry.arguments?.getString("lineId")?.toIntOrNull() ?: 1
            LineDetailScreen(
                lineId = lineId,
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        // 🚉 Lista de estaciones
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
                onBack = { navController.popBackStack() },
                languageViewModel = languageViewModel
            )
        }

        // 🏗️ Detalle de estación
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
                },
                languageViewModel = languageViewModel
            )
        }

        // ⭐ Favoritos
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { stationId ->
                    navController.navigate(Screen.StationDetail.createRoute(stationId.toString()))
                },
                onNavigateToRouteDetail = { origin, destination ->
                    // ✅ Navegación hacia la nueva ruta con origen y destino preseleccionados
                    navController.navigate(
                        Screen.RouteSelectionWithParams.createRoute(origin, destination)
                    )
                },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { navController.navigate(Screen.Routes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                languageViewModel = languageViewModel
            )
        }

        // ⚙️ Configuración
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
                onNavigateToAbout = { navController.navigate(Screen.About.route) },
                languageViewModel = languageViewModel
            )
        }

        // ℹ️ Acerca de
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
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                languageViewModel = languageViewModel
            )
        }

        // 🗺️ Pantalla de selección de rutas
        composable(Screen.Routes.route) {
            RouteSelectionScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { /* Ya estamos aquí */ },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                languageViewModel = languageViewModel
            )
        }

        // ✅ Nueva versión de selección de rutas con origen/destino
        composable(Screen.RouteSelectionWithParams.route) { backStackEntry ->
            val origin = backStackEntry.arguments?.getString("origin") ?: ""
            val destination = backStackEntry.arguments?.getString("destination") ?: ""

            RouteSelectionScreen(
                preselectedOrigin = origin,
                preselectedDestination = destination,
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToStations = { navController.navigate(Screen.Stations.route) },
                onNavigateToRoutes = { /* Ya estás en rutas */ },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                languageViewModel = languageViewModel
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
