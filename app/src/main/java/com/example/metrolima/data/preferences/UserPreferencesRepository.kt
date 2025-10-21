package com.example.metrolima.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.metrolima.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para crear el DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Repositorio para manejar las preferencias del usuario usando DataStore
 */
class UserPreferencesRepository(private val context: Context) {

    // Key para guardar el tema
    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    /**
     * Flow que emite el modo de tema actual
     * Por defecto es SYSTEM
     */
    val themeMode: Flow<ThemeMode> = context.dataStore.data
        .map { preferences ->
            val themeName = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
            try {
                ThemeMode.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            }
        }

    /**
     * Guarda el modo de tema seleccionado
     */
    suspend fun saveThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }

    /**
     * Obtiene el modo de tema actual de forma síncrona (para inicialización)
     */
    suspend fun getThemeMode(): ThemeMode {
        val preferences = context.dataStore.data.map { it[THEME_MODE_KEY] }
        return try {
            ThemeMode.valueOf(preferences.map { it ?: ThemeMode.SYSTEM.name }.toString())
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }
}