package com.example.metrolima.data.model

data class EstacionRemota(
    val nombre: String,
    val linea: String,
    val distrito: String,
    val horarioApertura: String,
    val horarioCierre: String,
    val latitud: Double,
    val longitud: Double
)
