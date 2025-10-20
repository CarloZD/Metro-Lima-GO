package com.example.metrolima.data.dao

import androidx.room.*
import com.example.metrolima.data.model.Estacion
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDao {

    @Query("SELECT * FROM estaciones ORDER BY nombre ASC")
    fun getAllEstaciones(): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE id = :id")
    suspend fun getEstacionById(id: Int): Estacion?

    @Query("SELECT * FROM estaciones WHERE nombre LIKE '%' || :query || '%' OR distrito LIKE '%' || :query || '%'")
    fun searchEstaciones(query: String): Flow<List<Estacion>>

    @Query("SELECT * FROM estaciones WHERE linea = :linea")
    fun getEstacionesByLinea(linea: String): Flow<List<Estacion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstacion(estacion: Estacion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEstaciones(estaciones: List<Estacion>)

    @Update
    suspend fun updateEstacion(estacion: Estacion)

    @Delete
    suspend fun deleteEstacion(estacion: Estacion)

    @Query("DELETE FROM estaciones")
    suspend fun deleteAllEstaciones()
}