package com.example.metrolima.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.presentation.components.BottomNavigationBar
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToStations: () -> Unit = {},
    onNavigateToRoutes: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    languageViewModel: LanguageViewModel = viewModel()
) {
    val context = LocalContext.current
    val isEnglish by languageViewModel.isEnglish.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringsManager.getString("metro_map", isEnglish),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 2, // “Rutas” activo
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = onNavigateToStations,
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = onNavigateToSettings,
                isEnglish = isEnglish
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Map,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = StringsManager.getString("open_metro_map", isEnglish),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:-12.0464,-77.0428?q=Metro+de+Lima")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                            setPackage("com.google.android.apps.maps")
                        }

                        if (mapIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(mapIntent)
                        } else {
                            // Abre en navegador si no hay Google Maps
                            val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            context.startActivity(browserIntent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        StringsManager.getString("open_google_maps", isEnglish),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
