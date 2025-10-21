package com.example.metrolima.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.presentation.viewmodel.EstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onStationClick: (Int) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: EstacionViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(1) }
    var selectedLine by remember { mutableStateOf("Todas") }
    var showFilterMenu by remember { mutableStateOf(false) }

    // Observar datos desde Room
    val estaciones by viewModel.estaciones.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Estaciones", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    // 🔽 Botón de filtro
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filtrar por línea",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    // Menú desplegable de líneas
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todas las líneas") },
                            onClick = {
                                selectedLine = "Todas"
                                viewModel.searchEstaciones("")
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Línea 1") },
                            onClick = {
                                selectedLine = "Línea 1"
                                viewModel.getEstacionesByLinea("Línea 1")
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Línea 2") },
                            onClick = {
                                selectedLine = "Línea 2"
                                viewModel.getEstacionesByLinea("Línea 2")
                                showFilterMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },

        // Barra inferior de navegación
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home", fontSize = 10.sp) },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        onNavigateToHome()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Train, contentDescription = null) },
                    label = { Text("Estaciones", fontSize = 10.sp) },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Map, contentDescription = null) },
                    label = { Text("Rutas", fontSize = 10.sp) },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onNavigateToRoutes()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Configuración", fontSize = 10.sp) },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        onNavigateToSettings()
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Chip de filtro activo
            if (selectedLine != "Todas") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = {
                            selectedLine = "Todas"
                            viewModel.searchEstaciones("")
                        },
                        label = { Text("Filtrando: $selectedLine", fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Quitar filtro",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }

            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchEstaciones(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar estación o distrito") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchEstaciones("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Conteo de resultados
            Text(
                "${estaciones.size} estaciones encontradas",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Mostrar lista o estados vacíos
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
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No se encontraron estaciones",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                            if (selectedLine != "Todas" || searchQuery.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = {
                                    selectedLine = "Todas"
                                    viewModel.searchEstaciones("")
                                }) {
                                    Text("Limpiar filtros")
                                }
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                        items(estaciones) { estacion ->
                            StationItem(
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

// ---- Tarjeta individual de estación ----
@Composable
fun StationItem(
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
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
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
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Train,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            line,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        district,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
