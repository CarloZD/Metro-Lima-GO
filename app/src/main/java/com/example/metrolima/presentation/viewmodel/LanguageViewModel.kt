package com.example.metrolima.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.metrolima.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja el estado del idioma de la aplicación
 * Solo maneja español e inglés
 */
class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesRepository = UserPreferencesRepository(application)

    private val _isEnglish = MutableStateFlow(false)
    val isEnglish: StateFlow<Boolean> = _isEnglish.asStateFlow()

    init {
        loadLanguagePreference()
    }

    private fun loadLanguagePreference() {
        viewModelScope.launch {
            preferencesRepository.getLanguagePreference().collect { languageCode ->
                _isEnglish.value = languageCode == "en"
            }
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val newLanguage = if (_isEnglish.value) "es" else "en"
            preferencesRepository.saveLanguagePreference(newLanguage)
            _isEnglish.value = !_isEnglish.value
        }
    }

    fun setLanguage(isEnglish: Boolean) {
        viewModelScope.launch {
            val languageCode = if (isEnglish) "en" else "es"
            preferencesRepository.saveLanguagePreference(languageCode)
            _isEnglish.value = isEnglish
        }
    }
}