package com.example.metrolima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.metrolima.presentation.screens.RouteDetailScreenWithButtons
import com.example.metrolima.ui.theme.MetroLimaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RouteDetailScreenWithButtons(
                        origin = "Estación Central",
                        destination = "San Juan",
                        onBack = {},
                        onSaveRoute = {}
                    )
                }
            }
        }
    }
}
