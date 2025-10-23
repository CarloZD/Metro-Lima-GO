package com.example.metrolima.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.R
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.presentation.viewmodel.EstacionViewModel
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.presentation.components.BottomNavigationBar
import com.example.metrolima.utils.StringsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteSelectionScreen(
    viewModel: EstacionViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    languageViewModel: LanguageViewModel = viewModel()
) {
    val estaciones by viewModel.estaciones.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isEnglish by languageViewModel.isEnglish.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadEstacionesRemotas()
    }

    var origen by remember { mutableStateOf<Estacion?>(null) }
    var destino by remember { mutableStateOf<Estacion?>(null) }
    var tiempoEstimado by remember { mutableStateOf<String?>(null) }
    var estacionesIntermedias by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = StringsManager.getString("route", isEnglish),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = StringsManager.getString("back", isEnglish))
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
                selectedItem = 2,
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = onNavigateToStations,
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = onNavigateToSettings,
                isEnglish = isEnglish
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // 🔹 Mapa placeholder (imagen superior)
                Image(
                    painter = painterResource(id = R.drawable.mapa_lima), // usa una imagen local o placeholder
                    contentDescription = "Mapa de la ruta",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = StringsManager.getString("trip_details", isEnglish),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // 🔹 Origen
                    InfoCard(
                        icon = Icons.Default.LocationOn,
                        title = StringsManager.getString("origin", isEnglish),
                        value = origen?.nombre ?: StringsManager.getString("select_station", isEnglish)
                    )

                    // 🔹 Destino
                    InfoCard(
                        icon = Icons.Default.Place,
                        title = StringsManager.getString("destination", isEnglish),
                        value = destino?.nombre ?: StringsManager.getString("select_station", isEnglish)
                    )

                    // 🔹 Dropdowns
                    EstacionDropdown(StringsManager.getString("select_origin", isEnglish), estaciones) { origen = it }
                    EstacionDropdown(StringsManager.getString("select_destination", isEnglish), estaciones) { destino = it }

                    // 🔹 Calcular ruta
                    Button(
                        onClick = {
                            if (origen != null && destino != null) {
                                val tiempo = (15..45).random()
                                val intermedias = (5..20).random()
                                tiempoEstimado = "$tiempo minutos"
                                estacionesIntermedias = intermedias
                            }
                        },
                        enabled = origen != null && destino != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.DirectionsTransit, null)
                        Spacer(Modifier.width(8.dp))
                        Text(StringsManager.getString("calculate_route", isEnglish))
                    }

                    // 🔹 Resultado
                    if (tiempoEstimado != null) {
                        InfoCard(
                            icon = Icons.Default.Schedule,
                            title = StringsManager.getString("estimated_time", isEnglish),
                            value = tiempoEstimado!!
                        )
                        InfoCard(
                            icon = Icons.Default.Train,
                            title = StringsManager.getString("intermediate_stations", isEnglish),
                            value = estacionesIntermedias.toString()
                        )

                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1976D2)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(StringsManager.getString("save_route", isEnglish), color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                Text(value, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun EstacionDropdown(label: String, estaciones: List<Estacion>, onSelect: (Estacion) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, null)
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            estaciones.forEach {
                DropdownMenuItem(
                    text = { Text(it.nombre) },
                    onClick = {
                        selectedName = it.nombre
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
