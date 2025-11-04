package com.example.metrolima.data.repository

import com.example.metrolima.data.dao.LineaDao
import com.example.metrolima.data.model.Linea
import kotlinx.coroutines.flow.Flow

class LineaRepository(private val lineaDao: LineaDao) {

    val allLineas: Flow<List<Linea>> = lineaDao.getAllLineas()

    suspend fun getLineaById(id: Int): Linea? {
        return lineaDao.getLineaById(id)
    }

    suspend fun getLineaByNumero(numero: Int): Linea? {
        return lineaDao.getLineaByNumero(numero)
    }

    suspend fun insertLinea(linea: Linea) {
        lineaDao.insertLinea(linea)
    }

    suspend fun updateLinea(linea: Linea) {
        lineaDao.updateLinea(linea)
    }

    suspend fun deleteLinea(linea: Linea) {
        lineaDao.deleteLinea(linea)
    }
}