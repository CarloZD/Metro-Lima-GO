package com.example.metrolima.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.metrolima.R
import com.example.metrolima.data.dao.EstacionDao
import com.example.metrolima.data.model.Estacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Estacion::class],
    version = 1,
    exportSchema = false
)
abstract class MetroDatabase : RoomDatabase() {

    abstract fun estacionDao(): EstacionDao

    companion object {
        @Volatile
        private var INSTANCE: MetroDatabase? = null

        fun getDatabase(context: Context): MetroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val context: Context
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.estacionDao())
                    }
                }
            }

            suspend fun populateDatabase(estacionDao: EstacionDao) {
                estacionDao.deleteAllEstaciones()

                // Datos iniciales de ejemplo (Línea 1 y Línea 2)
                val estaciones = listOf(
                    Estacion(
                        nombre = "Estación La Cultura",
                        linea = "Línea 1",
                        distrito = "San Borja",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.082167,
                        longitud = -76.995367,
                        imagenRes = R.drawable.estacion1
                    ),
                    Estacion(
                        nombre = "Estación Bayóvar",
                        linea = "Línea 1",
                        distrito = "San Juan de Lurigancho",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.955833,
                        longitud = -76.972500,
                        imagenRes = R.drawable.estacion2
                    ),
                    Estacion(
                        nombre = "Estación Santa Anita",
                        linea = "Línea 2",
                        distrito = "Santa Anita",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0525,
                        longitud = -76.9719,
                        imagenRes = R.drawable.estacion3
                    ),
                    Estacion(
                        nombre = "Estación Mercado de Santa Anita",
                        linea = "Línea 2",
                        distrito = "Santa Anita",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0551,
                        longitud = -76.9687,
                        imagenRes = R.drawable.estacion4
                    )
                )

                estacionDao.insertAllEstaciones(estaciones)
            }

        }
    }
}