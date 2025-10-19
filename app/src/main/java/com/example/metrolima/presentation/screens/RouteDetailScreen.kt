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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreenWithButtons(
    origin: String,
    destination: String,
    onBack: () -> Unit,
    onSaveRoute: () -> Unit
){
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Map, null, Modifier.size(64.dp), Color(0xFF2196F3))
                    Text("Mapa de la ruta", color = Color(0xFF2196F3), fontSize = 16.sp)
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

            Button(
                onClick = { onSaveRoute(); onBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Star, null, Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Guardar ruta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { onSaveRoute() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Star, null, Modifier.size(20.dp), Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Agregar a favoritos", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
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