package com.example.metrolima.data.model

data class EstacionRemota(
    val id: Int,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val horarioApertura: String,
    val horarioCierre: String,
    val alerta: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
)
