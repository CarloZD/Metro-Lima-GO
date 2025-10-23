package com.example.metrolima

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.presentation.navigation.MetroNavigation
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.ui.theme.MetroLimaTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val languageViewModel: LanguageViewModel = viewModel()
            val isEnglish by languageViewModel.isEnglish.collectAsState()

            // Configurar idioma en tiempo real
            LaunchedEffect(isEnglish) {
                val locale = if (isEnglish) Locale.ENGLISH else Locale("es")
                updateLocale(this@MainActivity, locale)
            }

            MetroLimaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MetroNavigation()
                }
            }
        }
    }

    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
