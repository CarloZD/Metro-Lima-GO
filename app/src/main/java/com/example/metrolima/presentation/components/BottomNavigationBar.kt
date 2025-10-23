package com.example.metrolima.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metrolima.utils.StringsManager

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onNavigateToHome: () -> Unit,
    onNavigateToStations: () -> Unit,
    onNavigateToRoutes: () -> Unit,
    onNavigateToSettings: () -> Unit,
    isEnglish: Boolean = false
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(StringsManager.getString("home", isEnglish), fontSize = 8.sp) },
            selected = selectedItem == 0,
            onClick = onNavigateToHome
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Train, contentDescription = "Estaciones") },
            label = { Text(StringsManager.getString("stations_nav", isEnglish), fontSize = 8.sp) },
            selected = selectedItem == 1,
            onClick = onNavigateToStations
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Rutas") },
            label = { Text(StringsManager.getString("routes", isEnglish), fontSize = 8.sp) },
            selected = selectedItem == 2,
            onClick = onNavigateToRoutes
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text(StringsManager.getString("configuration_nav", isEnglish), fontSize = 8.sp) },
            selected = selectedItem == 3,
            onClick = onNavigateToSettings
        )
    }
}
