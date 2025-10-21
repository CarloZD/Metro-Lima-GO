package com.example.metrolima.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.data.model.ThemeMode
import com.example.metrolima.presentation.viewmodel.ThemeViewModel

// Colores principales del tema
private val MetroBlue = Color(0xFF2196F3)
private val MetroCyan = Color(0xFF00BCD4)
private val MetroDarkBlue = Color(0xFF1E3A5F)
private val MetroOrange = Color(0xFFFF9800)
private val MetroGreen = Color(0xFF4CAF50)

private val DarkColorScheme = darkColorScheme(
    primary = MetroBlue,
    secondary = MetroCyan,
    tertiary = MetroOrange,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = MetroBlue,
    secondary = MetroCyan,
    tertiary = MetroOrange,
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun MetroLimaTheme(
    themeViewModel: ThemeViewModel = viewModel(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Observar el modo de tema desde el ViewModel
    val themeMode by themeViewModel.themeMode.collectAsState()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    // Determinar si usar tema oscuro según la preferencia del usuario
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}