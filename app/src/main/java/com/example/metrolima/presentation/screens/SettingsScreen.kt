package com.example.metrolima.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metrolima.data.model.ThemeMode
import com.example.metrolima.presentation.viewmodel.ThemeViewModel
import com.example.metrolima.presentation.viewmodel.LanguageViewModel
import com.example.metrolima.utils.StringsManager
import com.example.metrolima.presentation.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToStations: () -> Unit,
    onNavigateToRoutes: () -> Unit,
    onNavigateToAbout: () -> Unit = {},
    onNavigateToInformacion: () -> Unit = {},
    themeViewModel: ThemeViewModel = viewModel(),
    languageViewModel: LanguageViewModel = viewModel()
) {
    val currentThemeMode by themeViewModel.themeMode.collectAsState()
    val isEnglish by languageViewModel.isEnglish.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringsManager.getString("configuration", isEnglish),
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
                selectedItem = 3,
                onNavigateToHome = onNavigateToHome,
                onNavigateToStations = onNavigateToStations,
                onNavigateToRoutes = onNavigateToRoutes,
                onNavigateToSettings = {},
                isEnglish = isEnglish
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(vertical = 8.dp)
        ) {
            // 🔹 Tema Section
            SettingSection(
                title = StringsManager.getString("theme", isEnglish),
                description = StringsManager.getString("change_theme_description", isEnglish)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        StringsManager.getString("light_mode", isEnglish),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Switch(
                        checked = currentThemeMode == ThemeMode.DARK,
                        onCheckedChange = { isDark ->
                            themeViewModel.setThemeMode(
                                if (isDark) ThemeMode.DARK else ThemeMode.LIGHT
                            )
                        },
                        modifier = Modifier.scale(0.9f)
                    )

                    Text(
                        StringsManager.getString("dark_mode", isEnglish),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (currentThemeMode != ThemeMode.SYSTEM) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { themeViewModel.setThemeMode(ThemeMode.SYSTEM) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            StringsManager.getString("use_system_theme", isEnglish),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            // 🌐 Idioma Section
            SettingSection(
                title = StringsManager.getString("language", isEnglish),
                description = StringsManager.getString("select_language_description", isEnglish)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Español", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                    Switch(
                        checked = isEnglish,
                        onCheckedChange = { languageViewModel.setLanguage(it) },
                        modifier = Modifier.scale(0.9f)
                    )
                    Text(
                        StringsManager.getString("english", isEnglish),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            // ℹ️ Acerca de
            SettingItem(
                icon = Icons.Default.Info,
                title = StringsManager.getString("about", isEnglish),
                onClick = onNavigateToAbout
            )

            // ℹ️ Información Adicional  🔹 NUEVO BLOQUE
            SettingItem(
                icon = Icons.Default.Newspaper,
                title = StringsManager.getString("additional_info", isEnglish),
                onClick = onNavigateToInformacion
            )

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            // 🧩 Versión
            SettingItem(
                icon = Icons.Default.Info,
                title = StringsManager.getString("version", isEnglish),
                value = "1.0.0"
            )

            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}

@Composable
private fun SettingSection(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        content()
    }
}

@Composable
private fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        if (value != null) {
            Text(
                value,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        } else {
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
