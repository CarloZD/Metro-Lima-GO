package com.example.metrolima.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.presentation.components.BottomNavigationBar
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager

// ----------------------
// DATA CLASSES
// ----------------------
data class FavoriteRoute(
    val id: Int,
    val name: String,
    val line: String
)

data class FavoriteStation(
    val id: Int,
    val name: String,
    val line: String,
    val district: String
)

// ----------------------
// MAIN SCREEN
// ----------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToRouteDetail: (String, String) -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    languageViewModel: LanguageViewModel = viewModel()
) {
    val isEnglish by languageViewModel.isEnglish.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    var favoriteRoutes by remember {
        mutableStateOf(
            listOf(
                FavoriteRoute(1, "Villa El Salvador → San Juan", "Línea 1"),
                FavoriteRoute(2, "28 de Julio → Cabitos", "Línea 2"),
                FavoriteRoute(3, "Gamarra → Atocongo", "Línea 1"),
                FavoriteRoute(4, "San Juan → Villa El Salvador", "Línea 2")
            )
        )
    }

    var favoriteStations by remember {
        mutableStateOf(
            listOf(
                FavoriteStation(1, "Estación Villa El Salvador", "Línea 1", "Villa El Salvador"),
                FavoriteStation(2, "Estación 28 de Julio", "Línea 2", "La Victoria"),
                FavoriteStation(3, "Estación Cabitos", "Línea 1", "San Juan de Miraflores"),
                FavoriteStation(4, "Estación San Juan", "Línea 2", "San Juan de Miraflores")
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringsManager.getString("favorites", isEnglish),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = StringsManager.getString("back", isEnglish),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 2,
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = onNavigateToStations,
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = onNavigateToSettings,
                isEnglish = isEnglish
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text(StringsManager.getString("routes", isEnglish)) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text(StringsManager.getString("stations", isEnglish)) }
                )
            }

            when (selectedTab) {
                0 -> FavoriteRoutesList(
                    favoriteRoutes = favoriteRoutes,
                    onClick = { origin, destination -> onNavigateToRouteDetail(origin, destination) },
                    onDelete = { id -> favoriteRoutes = favoriteRoutes.filter { it.id != id } },
                    isEnglish = isEnglish
                )
                1 -> FavoriteStationsList(
                    favoriteStations = favoriteStations,
                    onClick = { id -> onNavigateToDetail(id) },
                    onDelete = { id -> favoriteStations = favoriteStations.filter { it.id != id } },
                    isEnglish = isEnglish
                )
            }
        }
    }
}

// ----------------------
// LISTAS
// ----------------------
@Composable
private fun FavoriteRoutesList(
    favoriteRoutes: List<FavoriteRoute>,
    onClick: (String, String) -> Unit,
    onDelete: (Int) -> Unit,
    isEnglish: Boolean
) {
    if (favoriteRoutes.isEmpty()) {
        EmptyState(
            icon = Icons.Default.Star,
            message = StringsManager.getString("no_favorite_routes", isEnglish),
            hint = StringsManager.getString("save_frequent_routes", isEnglish)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoriteRoutes) { route ->
                val (origin, destination) = route.name.split("→").map { it.trim() }
                FavoriteRouteItem(
                    route = route,
                    onClick = { onClick(origin, destination) },
                    onDelete = { onDelete(route.id) },
                    isEnglish = isEnglish
                )
            }
        }
    }
}

@Composable
private fun FavoriteStationsList(
    favoriteStations: List<FavoriteStation>,
    onClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    isEnglish: Boolean
) {
    if (favoriteStations.isEmpty()) {
        EmptyState(
            icon = Icons.Default.Train,
            message = StringsManager.getString("no_favorite_stations", isEnglish),
            hint = StringsManager.getString("save_frequent_stations", isEnglish)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoriteStations) { station ->
                FavoriteStationItem(
                    station = station,
                    onClick = { onClick(station.id) },
                    onDelete = { onDelete(station.id) },
                    isEnglish = isEnglish
                )
            }
        }
    }
}

// ----------------------
// COMPONENTES
// ----------------------
@Composable
private fun FavoriteRouteItem(
    route: FavoriteRoute,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    isEnglish: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(route.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(route.line, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar")
            }
        }
    }
}

@Composable
private fun FavoriteStationItem(
    station: FavoriteStation,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    isEnglish: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Train,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(station.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(station.line, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                    Text(station.district, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar")
            }
        }
    }
}

@Composable
private fun EmptyState(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String, hint: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))
            Text(message, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            Spacer(Modifier.height(4.dp))
            Text(hint, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        }
    }
}
