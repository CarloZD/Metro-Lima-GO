package com.example.metrolima.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreenWithButtons(
    origin: String,
    destination: String,
    onBack: () -> Unit,
    onSaveRoute: () -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ruta", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = onNavigateToStations,
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = onNavigateToSettings
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔹 Mapa clickeable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
                    .clickable {
                        val gmmIntentUri = Uri.parse("geo:-12.0464,-77.0428?q=Metro+de+Lima")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")

                        if (mapIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(mapIntent)
                        } else {
                            val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            context.startActivity(browserIntent)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Map, null, Modifier.size(64.dp), Color(0xFF2196F3))
                    Text("Mapa de la ruta (toca para abrir)", color = Color(0xFF2196F3), fontSize = 16.sp)
                }
            }

            Text("Detalles del viaje", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    DetailRow(Icons.Default.LocationOn, "Origen", origin.ifEmpty { "No especificado" })
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                    DetailRow(Icons.Default.Place, "Destino", destination.ifEmpty { "No especificado" })
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                    DetailRow(Icons.Default.Schedule, "Tiempo estimado", "45 minutos")
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                    DetailRow(Icons.Default.Train, "Estaciones intermedias", "15 estaciones")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSaveRoute() },
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Star, null, Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Guardar ruta", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = { onSaveRoute() },
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Favorite, null, Modifier.size(14.dp), Color.White)
                    Spacer(Modifier.width(4.dp))
                    Text("Favoritos", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(icon, null, tint = Color(0xFF2196F3), modifier = Modifier.size(28.dp))
        Column {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
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
        contentColor = Color(0xFF2196F3),
        modifier = Modifier.height(60.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(20.dp)) },
            label = { Text("Home", fontSize = 8.sp) },
            selected = selectedItem == 0,
            onClick = onNavigateToHome
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Train, contentDescription = "Estaciones", modifier = Modifier.size(20.dp)) },
            label = { Text("Estaciones", fontSize = 8.sp) },
            selected = selectedItem == 1,
            onClick = onNavigateToStations
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Rutas", modifier = Modifier.size(20.dp)) },
            label = { Text("Rutas", fontSize = 8.sp) },
            selected = selectedItem == 2,
            onClick = onNavigateToRoutes
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración", modifier = Modifier.size(20.dp)) },
            label = { Text("Configuración", fontSize = 8.sp) },
            selected = selectedItem == 3,
            onClick = onNavigateToSettings
        )
    }
}
