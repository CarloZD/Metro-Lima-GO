package com.example.metrolima

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.presentation.navigation.MetroNavigation
import com.example.metrolima.presentation.screens.NoWifiScreen
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.ui.theme.MetroLimaTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private fun isWifiConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val languageViewModel: LanguageViewModel = viewModel()
            val isEnglish by languageViewModel.isEnglish.collectAsState()
            var isWifiConnected by remember { mutableStateOf(isWifiConnected()) }

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
