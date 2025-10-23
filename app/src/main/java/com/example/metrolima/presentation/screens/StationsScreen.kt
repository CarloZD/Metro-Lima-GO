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
    var selectedLine by remember { mutableStateOf(StringsManager.getString("all_lines", isEnglish)) }
    var showFilterMenu by remember { mutableStateOf(false) }

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
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = StringsManager.getString("filter_by_line", isEnglish),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        listOf(
                            StringsManager.getString("all_lines", isEnglish),
                            "Línea 1",
                            "Línea 2"
                        ).forEach { line ->
                            DropdownMenuItem(
                                text = { Text(line) },
                                onClick = {
                                    selectedLine = line
                                    if (line == StringsManager.getString("all_lines", isEnglish)) {
                                        viewModel.searchEstaciones("")
                                    } else {
                                        viewModel.getEstacionesByLinea(line)
                                    }
                                    showFilterMenu = false
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // ✅ Azul igual que StationDetailScreen
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            // 🔹 Filtro activo
            if (selectedLine != StringsManager.getString("all_lines", isEnglish)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = {
                            selectedLine = StringsManager.getString("all_lines", isEnglish)
                            viewModel.searchEstaciones("")
                        },
                        label = {
                            Text(
                                "${StringsManager.getString("filtering", isEnglish)}: $selectedLine",
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.FilterList, null, Modifier.size(16.dp))
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Close, null, Modifier.size(16.dp))
                        }
                    )
                }
            }

            // 🔍 Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchEstaciones(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(StringsManager.getString("search_station_district", isEnglish)) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchEstaciones("") }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Text(
                "${estaciones.size} ${StringsManager.getString("stations_found", isEnglish)}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // 🔹 Contenido principal
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
                                StringsManager.getString("no_stations_found", isEnglish),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                            if (selectedLine != StringsManager.getString("all_lines", isEnglish)
                                || searchQuery.isNotEmpty()
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = {
                                    selectedLine = StringsManager.getString("all_lines", isEnglish)
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
                            bottom = 80.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
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

@Composable
private fun StationItem(
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        modifier = Modifier.size(28.dp)
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
                Spacer(modifier = Modifier.height(4.dp))
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
