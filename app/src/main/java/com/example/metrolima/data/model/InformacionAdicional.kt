package com.example.metrolima.data.model

data class Tarifa(
    val tipo: String,
    val precioSoles: Double,
    val descripcion: String
)

data class AvisoMantenimiento(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val lineasAfectadas: List<String>,
    val tipo: String
)

data class ConsejoSeguridad(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val icono: String
)