package com.example.metrolima.data.model

import java.util.Locale

/**
 * Enum que define los idiomas soportados por la aplicación
 */
enum class Language(
    val code: String,
    val displayName: String,
    val locale: Locale
) {
    SPANISH("es", "Español", Locale("es", "ES")),
    ENGLISH("en", "English", Locale("en", "US"));

    companion object {
        fun fromCode(code: String): Language {
            return values().find { it.code == code } ?: SPANISH
        }

        fun getSystemLanguage(): Language {
            val systemLanguage = Locale.getDefault().language
            return when (systemLanguage) {
                "en" -> ENGLISH
                "es" -> SPANISH
                else -> SPANISH
            }
        }
    }
}