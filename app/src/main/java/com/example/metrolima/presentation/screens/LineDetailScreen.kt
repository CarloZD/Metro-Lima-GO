package com.example.metrolima.presentation.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.data.database.MetroDatabase
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.model.Linea
import com.example.metrolima.data.model.getColor
import com.example.metrolima.data.repository.EstacionRepository
import com.example.metrolima.data.repository.LineaRepository
import com.example.metrolima.presentation.components.BottomNavigationBar
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineDetailScreen(
    lineId: Int,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    languageViewModel: LanguageViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isEnglish by languageViewModel.isEnglish.collectAsState()

    var linea by remember { mutableStateOf<Linea?>(null) }
    var estaciones by remember { mutableStateOf<List<Estacion>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showMap by remember { mutableStateOf(false) }

    // Cargar datos
    LaunchedEffect(lineId) {
        scope.launch {
            val database = MetroDatabase.getDatabase(context)
            val lineaRepo = LineaRepository(database.lineaDao())
            val estacionRepo = EstacionRepository(database.estacionDao())

            linea = lineaRepo.getLineaById(lineId)
            linea?.let { l ->
                estacionRepo.getEstacionesByLinea(l.nombre).collect { list ->
                    estaciones = list
                    isLoading = false
                }
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        linea?.nombre ?: "Línea",
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
                    IconButton(onClick = { showMap = !showMap }) {
                        Icon(
                            if (showMap) Icons.Default.List else Icons.Default.Map,
                            contentDescription = if (showMap) "Ver lista" else "Ver mapa",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = linea?.getColor() ?: MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 1,
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
            ) {
                // Información de la línea
                linea?.let { l ->
                    LineInfoCard(l, isEnglish)
                }

                // Mapa o Lista de estaciones
                if (showMap) {
                    MapViewComposable(
                        estaciones = estaciones,
                        lineColor = linea?.getColor() ?: Color.Blue,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    StationsList(
                        estaciones = estaciones,
                        lineColor = linea?.getColor() ?: Color.Blue,
                        modifier = Modifier.weight(1f),
                        isEnglish = isEnglish
                    )
                }
            }
        }
    }
}

@Composable
private fun LineInfoCard(linea: Linea, isEnglish: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(linea.getColor()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        linea.numero.toString(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    linea.descripcion,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    icon = Icons.Default.PlayArrow,
                    label = if (isEnglish) "Start" else "Inicio",
                    value = linea.estacionInicio
                )
                InfoItem(
                    icon = Icons.Default.Flag,
                    label = if (isEnglish) "End" else "Fin",
                    value = linea.estacionFin
                )
            }

            Surface(
                color = getEstadoColor(linea.estado),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (isEnglish) translateEstado(linea.estado) else linea.estado,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.width(4.dp))
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun StationsList(
    estaciones: List<Estacion>,
    lineColor: Color,
    modifier: Modifier = Modifier,
    isEnglish: Boolean
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "${estaciones.size} ${if (isEnglish) "stations" else "estaciones"}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(estaciones.size) { index ->
            StationListItem(
                estacion = estaciones[index],
                lineColor = lineColor,
                position = index + 1,
                isLast = index == estaciones.size - 1
            )
        }
    }
}

@Composable
private fun StationListItem(
    estacion: Estacion,
    lineColor: Color,
    position: Int,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(lineColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    position.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(32.dp)
                        .background(lineColor.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    estacion.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    estacion.distrito,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MapViewComposable(
    estaciones: List<Estacion>,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            initOSMConfig(ctx)

            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                // Configurar el mapa centrado en Lima
                controller.setZoom(12.0)
                controller.setCenter(GeoPoint(-12.0464, -77.0428))

                // Dibujar línea conectando estaciones
                if (estaciones.isNotEmpty()) {
                    val line = Polyline().apply {
                        outlinePaint.color = android.graphics.Color.parseColor(
                            String.format("#%06X", 0xFFFFFF and lineColor.hashCode())
                        )
                        outlinePaint.strokeWidth = 8f
                    }

                    estaciones.forEach { estacion ->
                        val point = GeoPoint(estacion.latitud, estacion.longitud)
                        line.addPoint(point)

                        // Agregar marcador para cada estación
                        val marker = Marker(this).apply {
                            position = point
                            title = estacion.nombre
                            snippet = estacion.distrito
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                        overlays.add(marker)
                    }

                    overlays.add(line)

                    // Centrar mapa en la primera estación
                    controller.setCenter(GeoPoint(estaciones[0].latitud, estaciones[0].longitud))
                }
            }
        }
    )
}

private fun initOSMConfig(context: Context) {
    Configuration.getInstance().apply {
        userAgentValue = context.packageName
        load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    }
}

private fun getEstadoColor(estado: String): Color {
    return when (estado) {
        "Operativa" -> Color(0xFF4CAF50)
        "En construcción" -> Color(0xFFFFC107)
        "Planificada" -> Color(0xFF2196F3)
        else -> Color.Gray
    }
}

private fun translateEstado(estado: String): String {
    return when (estado) {
        "Operativa" -> "Operational"
        "En construcción" -> "Under construction"
        "Planificada" -> "Planned"
        else -> estado
    }
}