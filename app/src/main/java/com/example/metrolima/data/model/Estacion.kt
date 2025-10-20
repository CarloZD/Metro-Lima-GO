package com.example.metrolima.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val horarioApertura: String = "05:00 AM",
    val horarioCierre: String = "10:00 PM",
    val latitud: Double = -12.046374,
    val longitud: Double = -77.042793,
    val imagenRes: Int = 0
)