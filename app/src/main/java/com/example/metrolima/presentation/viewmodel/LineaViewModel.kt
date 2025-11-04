package com.example.metrolima.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.metrolima.data.database.MetroDatabase
import com.example.metrolima.data.model.Linea
import com.example.metrolima.data.repository.LineaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LineaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LineaRepository

    private val _lineas = MutableStateFlow<List<Linea>>(emptyList())
    val lineas: StateFlow<List<Linea>> = _lineas.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val lineaDao = MetroDatabase.getDatabase(application).lineaDao()
        repository = LineaRepository(lineaDao)
        loadLineas()
    }

    private fun loadLineas() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.allLineas.collect { lineas ->
                _lineas.value = lineas
                _isLoading.value = false
            }
        }
    }

    suspend fun getLineaById(id: Int): Linea? {
        return repository.getLineaById(id)
    }
}