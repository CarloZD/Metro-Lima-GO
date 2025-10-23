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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.data.database.MetroDatabase
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.repository.EstacionRepository
import com.example.metrolima.presentation.components.BottomNavigationBar
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(
    stationId: Int,
    onBack: () -> Unit,
    onNavigateToRoute: (String, String) -> Unit,
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    languageViewModel: LanguageViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isEnglish by languageViewModel.isEnglish.collectAsState()

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
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringsManager.getString("station", isEnglish),
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
                    containerColor = MaterialTheme.colorScheme.primary
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
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            val currentEstacion = estacion
            if (currentEstacion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen superior
                    if (currentEstacion.imagenRes != 0) {
                        Image(
                            painter = painterResource(id = currentEstacion.imagenRes),
                            contentDescription = currentEstacion.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            currentEstacion.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Detalles de la estación
                        DetailRow(
                            Icons.Default.Train,
                            currentEstacion.linea
                        )
                        DetailRow(
                            Icons.Default.LocationOn,
                            currentEstacion.distrito
                        )
                        DetailRow(
                            Icons.Default.Public,
                            "Lat: ${currentEstacion.latitud}, Lon: ${currentEstacion.longitud}"
                        )
                        DetailRow(
                            Icons.Default.AccessTime,
                            "${currentEstacion.horarioApertura} - ${currentEstacion.horarioCierre}"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botones inferiores
                        Button(
                            onClick = onNavigateToFavorites,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                StringsManager.getString("add_to_favorites", isEnglish),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                onNavigateToRoute(currentEstacion.nombre, "Destino")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.DirectionsTransit, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                StringsManager.getString("view_routes", isEnglish),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            } else {
                // Estado cuando no se encuentra la estación
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            StringsManager.getString("station_not_found", isEnglish),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onBack) {
                            Text(StringsManager.getString("back", isEnglish))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
