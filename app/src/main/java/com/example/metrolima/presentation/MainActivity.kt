package com.example.metrolima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.metrolima.presentation.navigation.MetroNavigation
import com.example.metrolima.ui.theme.MetroLimaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MetroNavigation() // app con navegación
                }
            }
        }
    }
}
