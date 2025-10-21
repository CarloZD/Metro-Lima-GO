package com.example.metrolima.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.metrolima.data.model.ThemeMode
import com.example.metrolima.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja el estado del tema de la aplicación
 */
class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesRepository = UserPreferencesRepository(application)

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        loadThemePreference()
    }

    /**
     * Carga la preferencia de tema guardada
     */
    private fun loadThemePreference() {
        viewModelScope.launch {
            preferencesRepository.themeMode.collect { mode ->
                _themeMode.value = mode
            }
        }
    }

    /**
     * Cambia el modo de tema
     */
    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferencesRepository.saveThemeMode(mode)
            _themeMode.value = mode
        }
    }

    /**
     * Alterna entre modo claro y oscuro
     * Si está en SYSTEM, cambia a DARK
     * Si está en DARK, cambia a LIGHT
     * Si está en LIGHT, cambia a DARK
     */
    fun toggleTheme() {
        val newMode = when (_themeMode.value) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.DARK
        }
        setThemeMode(newMode)
    }

    /**
     * Determina si debe usar tema oscuro basado en el modo actual
     * y la configuración del sistema
     */
    fun shouldUseDarkTheme(isSystemInDarkTheme: Boolean): Boolean {
        return when (_themeMode.value) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> isSystemInDarkTheme
        }
    }
}