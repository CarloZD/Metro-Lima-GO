package com.example.metrolima.data.dao

import androidx.room.*
import com.example.metrolima.data.model.Linea
import kotlinx.coroutines.flow.Flow

@Dao
interface LineaDao {

    @Query("SELECT * FROM lineas ORDER BY numero ASC")
    fun getAllLineas(): Flow<List<Linea>>

    @Query("SELECT * FROM lineas WHERE id = :id")
    suspend fun getLineaById(id: Int): Linea?

    @Query("SELECT * FROM lineas WHERE numero = :numero")
    suspend fun getLineaByNumero(numero: Int): Linea?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinea(linea: Linea)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLineas(lineas: List<Linea>)

    @Update
    suspend fun updateLinea(linea: Linea)

    @Delete
    suspend fun deleteLinea(linea: Linea)

    @Query("DELETE FROM lineas")
    suspend fun deleteAllLineas()
}