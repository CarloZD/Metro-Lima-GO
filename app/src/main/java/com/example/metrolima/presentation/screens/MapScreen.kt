package com.example.metrolima.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MapScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Abrir mapa del Metro de Lima",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:-12.0464,-77.0428?q=Metro+de+Lima")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        // Si no hay Google Maps, abre en navegador
                        val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        context.startActivity(browserIntent)
                    }
                }
            ) {
                Text("Abrir en Google Maps")
            }
        }
    }
}
