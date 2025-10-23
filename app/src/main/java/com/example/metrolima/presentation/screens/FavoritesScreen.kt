package com.example.metrolima.presentation.screens

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
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
                        fontSize = 20.sp
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 2, // resalta el ítem "Rutas"
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
                    text = { Text(StringsManager.getString("routes", isEnglish), fontSize = 14.sp) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text(StringsManager.getString("stations", isEnglish), fontSize = 14.sp) }
                )
            }

            // Contenido según pestaña seleccionada
            when (selectedTab) {
                0 -> FavoriteRoutesList(
                    favoriteRoutes = favoriteRoutes,
                    onDelete = { id -> favoriteRoutes = favoriteRoutes.filter { it.id != id } },
                    isEnglish = isEnglish
                )

                1 -> FavoriteStationsList(
                    favoriteStations = favoriteStations,
                    onDelete = { id -> favoriteStations = favoriteStations.filter { it.id != id } },
                    isEnglish = isEnglish
                )
            }
        }
    }
}

@Composable
private fun FavoriteRoutesList(
    favoriteRoutes: List<FavoriteRoute>,
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
                FavoriteRouteItem(
                    route = route,
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
                    onDelete = { onDelete(station.id) },
                    isEnglish = isEnglish
                )
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            Text(
                message,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                hint,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
private fun FavoriteRouteItem(route: FavoriteRoute, onDelete: () -> Unit, isEnglish: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFFF9C4), shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column {
                    Text(route.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(route.line, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                }

            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = StringsManager.getString("delete", isEnglish))
            }
        }
    }
}


@Composable
private fun FavoriteStationItem(station: FavoriteStation, onDelete: () -> Unit, isEnglish: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Train,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column {
                    Text(station.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(station.line, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        station.district,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = StringsManager.getString("delete", isEnglish))
            }
        }
    }
}
