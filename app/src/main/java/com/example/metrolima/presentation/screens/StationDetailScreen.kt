package com.example.metrolima.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metrolima.data.database.MetroDatabase
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.repository.EstacionRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(
    stationId: Int,
    onBack: () -> Unit,
    onNavigateToRoute: (String, String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var estacion by remember { mutableStateOf<Estacion?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar estación desde Room
    LaunchedEffect(stationId) {
        scope.launch {
            val database = MetroDatabase.getDatabase(context)
            val repository = EstacionRepository(database.estacionDao())
            estacion = repository.getEstacionById(stationId)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estación", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF2196F3))
            }
        } else {
            val currentEstacion = estacion
            if (currentEstacion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen de la estación
                    if (currentEstacion.imagenRes != 0) {
                        Image(
                            painter = painterResource(id = currentEstacion.imagenRes),
                            contentDescription = currentEstacion.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Nombre de la estación
                        Text(
                            currentEstacion.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Detalles
                        DetailRow(Icons.Default.Train, currentEstacion.linea)
                        DetailRow(Icons.Default.LocationOn, currentEstacion.distrito)
                        DetailRow(
                            Icons.Default.Public,
                            "Lat: ${currentEstacion.latitud}, Lon: ${currentEstacion.longitud}"
                        )
                        DetailRow(
                            Icons.Default.AccessTime,
                            "${currentEstacion.horarioApertura} - ${currentEstacion.horarioCierre}"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón para planificar ruta
                        Button(
                            onClick = {
                                onNavigateToRoute(currentEstacion.nombre, "")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2196F3)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Agregar a favoritos", color = Color.White)
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Estación no encontrada",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onBack) {
                            Text("Volver")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF2196F3))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 14.sp)
    }
}