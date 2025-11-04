package com.example.metrolima.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color

@Entity(tableName = "lineas")
data class Linea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val numero: Int, // 1, 2, 3, etc.
    val colorHex: String,
    val estado: String, // "Operativa", "En construcción", "Planificada"
    val descripcion: String = "",
    val estacionInicio: String = "",
    val estacionFin: String = ""
)

// Función auxiliar para convertir hex a Color de Compose
fun Linea.getColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (e: Exception) {
        Color.Gray
    }
}