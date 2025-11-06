package com.example.metrolima.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.metrolima.data.model.AvisoMantenimiento
import com.example.metrolima.data.model.ConsejoSeguridad
import com.example.metrolima.data.model.Tarifa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InformacionViewModel : ViewModel() {

    private val _tarifas = MutableStateFlow<List<Tarifa>>(emptyList())
    val tarifas: StateFlow<List<Tarifa>> = _tarifas.asStateFlow()

    private val _avisos = MutableStateFlow<List<AvisoMantenimiento>>(emptyList())
    val avisos: StateFlow<List<AvisoMantenimiento>> = _avisos.asStateFlow()

    private val _consejos = MutableStateFlow<List<ConsejoSeguridad>>(emptyList())
    val consejos: StateFlow<List<ConsejoSeguridad>> = _consejos.asStateFlow()

    init {
        cargarTarifas()
        cargarAvisos()
        cargarConsejos()
    }

    private fun cargarTarifas() {
        _tarifas.value = listOf(
            Tarifa(
                tipo = "Tarifa General",
                precioSoles = 1.50,
                descripcion = "Adultos (18-59 años)"
            ),
            Tarifa(
                tipo = "Tarifa Universitaria",
                precioSoles = 0.75,
                descripcion = "Estudiantes universitarios con carnet"
            ),
            Tarifa(
                tipo = "Tarifa Escolar",
                precioSoles = 0.50,
                descripcion = "Estudiantes de colegio"
            ),
            Tarifa(
                tipo = "Tarifa Preferencial",
                precioSoles = 0.50,
                descripcion = "Adultos mayores (60+) y personas con discapacidad"
            ),
            Tarifa(
                tipo = "Tarifa Niños",
                precioSoles = 0.00,
                descripcion = "Menores de 5 años (gratis)"
            )
        )
    }

    private fun cargarAvisos() {
        _avisos.value = listOf(
            AvisoMantenimiento(
                id = 1,
                titulo = "Mantenimiento programado - Línea 1",
                descripcion = "Se realizará mantenimiento preventivo en las estaciones Villa El Salvador y Villa María del Triunfo.",
                fecha = "15 de Noviembre, 2025",
                lineasAfectadas = listOf("Línea 1"),
                tipo = "Mantenimiento"
            ),
            AvisoMantenimiento(
                id = 2,
                titulo = "Horario reducido - Línea 2",
                descripcion = "Por trabajos de mejora en la infraestructura, la Línea 2 operará en horario reducido.",
                fecha = "20 de Noviembre, 2025",
                lineasAfectadas = listOf("Línea 2"),
                tipo = "Mantenimiento"
            )
        )
    }

    private fun cargarConsejos() {
        _consejos.value = listOf(
            ConsejoSeguridad(
                id = 1,
                titulo = "Mantén tus pertenencias seguras",
                descripcion = "Mantén tus pertenencias siempre a la vista y cerca de ti. No dejes objetos de valor desatendidos.",
                icono = "security"
            ),
            ConsejoSeguridad(
                id = 2,
                titulo = "Respeta la línea amarilla",
                descripcion = "Mantente detrás de la línea amarilla en el andén mientras esperas el tren.",
                icono = "warning"
            ),
            ConsejoSeguridad(
                id = 3,
                titulo = "Deja salir primero",
                descripcion = "Permite que los pasajeros salgan del vagón antes de ingresar.",
                icono = "exit"
            ),
            ConsejoSeguridad(
                id = 4,
                titulo = "Cede el asiento prioritario",
                descripcion = "Los asientos preferenciales están destinados a adultos mayores, mujeres embarazadas y personas con discapacidad.",
                icono = "accessible"
            ),
            ConsejoSeguridad(
                id = 5,
                titulo = "No obstruyas las puertas",
                descripcion = "Evita obstruir las puertas del tren. Permite su correcto cierre para la seguridad de todos.",
                icono = "door"
            ),
            ConsejoSeguridad(
                id = 6,
                titulo = "Reporta situaciones sospechosas",
                descripcion = "Si observas algún comportamiento sospechoso o paquetes sin dueño, notifica inmediatamente al personal de seguridad.",
                icono = "report"
            ),
            ConsejoSeguridad(
                id = 7,
                titulo = "Mantén limpio el metro",
                descripcion = "No consumas alimentos ni bebidas en los vagones. Ayuda a mantener limpio el sistema de transporte.",
                icono = "clean"
            ),
            ConsejoSeguridad(
                id = 8,
                titulo = "En caso de emergencia",
                descripcion = "Mantén la calma y sigue las instrucciones del personal. Ubica las salidas de emergencia más cercanas.",
                icono = "emergency"
            )
        )
    }

    // Métodos para actualizar datos si fuera necesario
    fun actualizarTarifas(nuevasTarifas: List<Tarifa>) {
        _tarifas.value = nuevasTarifas
    }

    fun actualizarAvisos(nuevosAvisos: List<AvisoMantenimiento>) {
        _avisos.value = nuevosAvisos
    }

    fun actualizarConsejos(nuevosConsejos: List<ConsejoSeguridad>) {
        _consejos.value = nuevosConsejos
    }
}