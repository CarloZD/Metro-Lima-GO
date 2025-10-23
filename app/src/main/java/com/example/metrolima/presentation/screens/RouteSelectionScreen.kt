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

    LaunchedEffect(Unit) { viewModel.loadEstacionesRemotas() }

    var origen by remember { mutableStateOf<Estacion?>(null) }
    var destino by remember { mutableStateOf<Estacion?>(null) }
    var tiempoEstimado by remember { mutableStateOf<String?>(null) }
    var estacionesIntermedias by remember { mutableStateOf(0) }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = StringsManager.getString("route", isEnglish)
                            .lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
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
                    containerColor = MaterialTheme.colorScheme.primary, // Igual que StationsScreen
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mapa_lima),
                    contentDescription = "Mapa de la ruta",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = StringsManager.getString("trip_details", isEnglish),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        icon = Icons.Default.LocationOn,
                        title = StringsManager.getString("origin", isEnglish),
                        value = origen?.nombre ?: StringsManager.getString("select_station", isEnglish)
                    )
                    InfoCard(
                        icon = Icons.Default.Place,
                        title = StringsManager.getString("destination", isEnglish),
                        value = destino?.nombre ?: StringsManager.getString("select_station", isEnglish)
                    )

                    EstacionDropdown(
                        label = StringsManager.getString("select_origin", isEnglish),
                        estaciones = estaciones
                    ) { origen = it }

                    EstacionDropdown(
                        label = StringsManager.getString("select_destination", isEnglish),
                        estaciones = estaciones
                    ) { destino = it }

                    Button(
                        onClick = {
                            if (origen != null && destino != null) {
                                val tiempo = (15..45).random()
                                val intermedias = (5..20).random()
                                tiempoEstimado = "$tiempo min"
                                estacionesIntermedias = intermedias
                            }
                        },
                        enabled = origen != null && destino != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.DirectionsTransit, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(StringsManager.getString("calculate_route", isEnglish))
                    }

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
                    }
                }

                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
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
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
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
