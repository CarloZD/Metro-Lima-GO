package com.example.metrolima.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.metrolima.data.database.MetroDatabase
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.repository.EstacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EstacionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EstacionRepository

    private val _estaciones = MutableStateFlow<List<Estacion>>(emptyList())
    val estaciones: StateFlow<List<Estacion>> = _estaciones.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val estacionDao = MetroDatabase.getDatabase(application).estacionDao()
        repository = EstacionRepository(estacionDao)
        loadEstaciones()
    }

    private fun loadEstaciones() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.allEstaciones.collect { estaciones ->
                _estaciones.value = estaciones
                _isLoading.value = false
            }
        }
    }

    fun searchEstaciones(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isEmpty()) {
                repository.allEstaciones.collect { estaciones ->
                    _estaciones.value = estaciones
                }
            } else {
                repository.searchEstaciones(query).collect { estaciones ->
                    _estaciones.value = estaciones
                }
            }
        }
    }

    fun getEstacionesByLinea(linea: String) {
        viewModelScope.launch {
            repository.getEstacionesByLinea(linea).collect { estaciones ->
                _estaciones.value = estaciones
            }
        }
    }

    fun insertEstacion(estacion: Estacion) {
        viewModelScope.launch {
            repository.insertEstacion(estacion)
        }
    }

    fun actualizarDesdeAPI() {
        viewModelScope.launch {
            repository.refreshEstacionesDesdeAPI()
            loadEstaciones()
        }
    }
}