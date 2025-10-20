package com.example.metrolima.data.repository

import com.example.metrolima.data.dao.EstacionDao
import com.example.metrolima.data.model.Estacion
import kotlinx.coroutines.flow.Flow

class EstacionRepository(private val estacionDao: EstacionDao) {

    val allEstaciones: Flow<List<Estacion>> = estacionDao.getAllEstaciones()

    fun searchEstaciones(query: String): Flow<List<Estacion>> {
        return estacionDao.searchEstaciones(query)
    }

    fun getEstacionesByLinea(linea: String): Flow<List<Estacion>> {
        return estacionDao.getEstacionesByLinea(linea)
    }

    suspend fun getEstacionById(id: Int): Estacion? {
        return estacionDao.getEstacionById(id)
    }

    suspend fun insertEstacion(estacion: Estacion) {
        estacionDao.insertEstacion(estacion)
    }

    suspend fun updateEstacion(estacion: Estacion) {
        estacionDao.updateEstacion(estacion)
    }

    suspend fun deleteEstacion(estacion: Estacion) {
        estacionDao.deleteEstacion(estacion)
    }
}