package com.example.metrolima.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FavoriteRouteItem(
    val id: Int,
    val name: String,
    val line: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreen(
    origin: String,
    destination: String,
    onBack: () -> Unit,
    onSaveRoute: () -> Unit
) {
    val favoriteRoutes = remember {
        listOf(
            FavoriteRouteItem(1, "Estación Villa El Salvador", "Línea 1"),
            FavoriteRouteItem(2, "Estación 28 de Julio", "Línea 2"),
            FavoriteRouteItem(3, "Estación Cabitos", "Línea 1"),
            FavoriteRouteItem(4, "Estación San Juan", "Línea 2")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ruta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 2,
                onNavigateToHome = onBack,
                onNavigateToStations = { },
                onNavigateToRoutes = { },
                onNavigateToSettings = { }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Map Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Map,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFF2196F3)
                    )
                    Text(
                        "Mapa de la ruta",
                        color = Color(0xFF2196F3),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Detalles del viaje",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DetailItem(
                            icon = Icons.Default.LocationOn,
                            title = "Origen",
                            subtitle = if (origin.isEmpty()) "No especificado" else origin
                        )

                        Divider(color = Color.LightGray, thickness = 0.5.dp)

                        DetailItem(
                            icon = Icons.Default.Place,
                            title = "Destino",
                            subtitle = if (destination.isEmpty()) "No especificado" else destination
                        )

                        Divider(color = Color.LightGray, thickness = 0.5.dp)

                        DetailItem(
                            icon = Icons.Default.Schedule,
                            title = "Tiempo estimado",
                            subtitle = "45 minutos"
                        )

                        Divider(color = Color.LightGray, thickness = 0.5.dp)

                        DetailItem(
                            icon = Icons.Default.Train,
                            title = "Estaciones intermedias",
                            subtitle = "15 estaciones"
                        )
                    }
                }

                // Save Route Button
                Button(
                    onClick = {
                        onSaveRoute()
                        onBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Guardar ruta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Add to Favorites Button
                Button(
                    onClick = {
                        onSaveRoute()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Agregar a favoritos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Favorite Routes Section Title
                Text(
                    "Rutas favoritas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.paddingFromBaseline(top = 24.dp)
                )

                // Favorite Routes List
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    favoriteRoutes.forEach { route ->
                        FavoriteRouteCard(route = route)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2196F3),
            modifier = Modifier.size(28.dp)
        )

        Column {
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                subtitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun FavoriteRouteCard(route: FavoriteRouteItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
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
                            color = Color(0xFFFFF9C4),
                            shape = RoundedCornerShape(12.dp)
                        ),
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
                    Text(
                        route.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        route.line,
                        fontSize = 14.sp,
                        color = Color(0xFF2196F3)
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Eliminar",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: Int,
    onNavigateToHome: () -> Unit,
    onNavigateToStations: () -> Unit,
    onNavigateToRoutes: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF2196F3)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 10.sp) },
            selected = selectedItem == 0,
            onClick = onNavigateToHome
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Train, contentDescription = "Estaciones") },
            label = { Text("Estaciones", fontSize = 10.sp) },
            selected = selectedItem == 1,
            onClick = onNavigateToStations
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Rutas") },
            label = { Text("Rutas", fontSize = 10.sp) },
            selected = selectedItem == 2,
            onClick = onNavigateToRoutes
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Configuración", fontSize = 10.sp) },
            selected = selectedItem == 3,
            onClick = onNavigateToSettings
        )
    }
}