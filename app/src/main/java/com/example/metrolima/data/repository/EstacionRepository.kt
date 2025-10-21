package com.example.metrolima.data.repository

import com.example.metrolima.data.dao.EstacionDao
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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
    suspend fun refreshEstacionesDesdeAPI() {
        withContext(Dispatchers.IO) {
            try {
                val estacionesRemotas = RetrofitInstance.api.getEstacionesRemotas()
                val entidadesLocales = estacionesRemotas.map {
                    Estacion(
                        nombre = it.nombre,
                        linea = it.linea,
                        distrito = it.distrito,
                        horarioApertura = it.horarioApertura,
                        horarioCierre = it.horarioCierre,
                        latitud = it.latitud ?: -12.046374,
                        longitud = it.longitud ?: -77.042793
                    )
                }
                estacionDao.deleteAllEstaciones()
                entidadesLocales.forEach { estacionDao.insertEstacion(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}