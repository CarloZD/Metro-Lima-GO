package com.example.metrolima.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.metrolima.presentation.viewmodel.EstacionViewModel
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager
import com.example.metrolima.presentation.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onStationClick: (Int) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: EstacionViewModel = viewModel(),
    languageViewModel: LanguageViewModel = viewModel()
) {
    val isEnglish by languageViewModel.isEnglish.collectAsState()
    var selectedLine by remember { mutableStateOf("Todas") }

    val estaciones by viewModel.estaciones.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = StringsManager.getString("stations", isEnglish)
                            .lowercase()
                            .replaceFirstChar { it.titlecase() },
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 1,
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = {},
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = onNavigateToSettings,
                isEnglish = isEnglish
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            // 🔍 Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchEstaciones(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(
                        StringsManager.getString("search_station_district", isEnglish),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, null, Modifier.size(20.dp)) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchEstaciones("") }) {
                            Icon(Icons.Default.Clear, null, Modifier.size(20.dp))
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )

            // 🔹 Filtros de línea (Chips horizontales)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LineFilterChip(
                    label = "Todas",
                    isSelected = selectedLine == "Todas",
                    onClick = {
                        selectedLine = "Todas"
                        viewModel.searchEstaciones("")
                    }
                )
                LineFilterChip(
                    label = "Línea 1",
                    isSelected = selectedLine == "Línea 1",
                    onClick = {
                        selectedLine = "Línea 1"
                        viewModel.getEstacionesByLinea("Línea 1")
                    }
                )
                LineFilterChip(
                    label = "Línea 2",
                    isSelected = selectedLine == "Línea 2",
                    onClick = {
                        selectedLine = "Línea 2"
                        viewModel.getEstacionesByLinea("Línea 2")
                    }
                )
                LineFilterChip(
                    label = "Línea 3",
                    isSelected = selectedLine == "Línea 3",
                    onClick = {
                        selectedLine = "Línea 3"
                        viewModel.getEstacionesByLinea("Línea 3")
                    }
                )
            }

            // Contador de estaciones
            Text(
                "${estaciones.size} ${StringsManager.getString("stations_found", isEnglish)}",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // 🔹 Lista de estaciones
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                estaciones.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                StringsManager.getString("no_stations_found", isEnglish),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            if (selectedLine != "Todas" || searchQuery.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = {
                                    selectedLine = "Todas"
                                    viewModel.searchEstaciones("")
                                }) {
                                    Text(StringsManager.getString("clear_filters", isEnglish))
                                }
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(estaciones) { estacion ->
                            ModernStationCard(
                                id = estacion.id,
                                name = estacion.nombre,
                                line = estacion.linea,
                                district = estacion.distrito,
                                imageRes = estacion.imagenRes,
                                onClick = { onStationClick(estacion.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 🎨 Chip moderno para filtros de línea
@Composable
private fun LineFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(32.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFF2196F3) else Color.White,
        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)) else null
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

// 🎴 Card moderno para estaciones (como la imagen de referencia)
@Composable
private fun ModernStationCard(
    id: Int,
    name: String,
    line: String,
    district: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de la estación
            if (imageRes != 0) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Train,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información de la estación
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Badge de línea
                    Surface(
                        color = getLineColor(line),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            line,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        district,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

// 🎨 Colores por línea
private fun getLineColor(line: String): Color {
    return when {
        line.contains("1") -> Color(0xFF4CAF50) // Verde
        line.contains("2") -> Color(0xFFFFC107) // Amarillo
        line.contains("3") -> Color(0xFF00BCD4) // Celeste
        line.contains("4") -> Color(0xFFF44336) // Rojo
        line.contains("5") -> Color(0xFFE91E63) // Rosa
        line.contains("6") -> Color(0xFF9C27B0) // Morado
        else -> Color(0xFF2196F3) // Azul por defecto
    }
}