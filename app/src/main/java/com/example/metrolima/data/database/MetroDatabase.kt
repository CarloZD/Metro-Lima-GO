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
                // Limpiar la base de datos
                estacionDao.deleteAllEstaciones()

                // Insertar estaciones iniciales
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
                        nombre = "Estación Villa El Salvador",
                        linea = "Línea 1",
                        distrito = "Villa El Salvador",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.215833,
                        longitud = -76.938611,
                        imagenRes = R.drawable.estacion3
                    ),
                    Estacion(
                        nombre = "Estación Los Jardines",
                        linea = "Línea 1",
                        distrito = "San Juan de Lurigancho",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.987500,
                        longitud = -76.977778,
                        imagenRes = R.drawable.estacion4
                    ),
                    Estacion(
                        nombre = "Estación San Carlos",
                        linea = "Línea 1",
                        distrito = "San Juan de Lurigancho",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.999444,
                        longitud = -76.983889,
                        imagenRes = R.drawable.estacion5
                    ),
                    Estacion(
                        nombre = "Estación Grau",
                        linea = "Línea 1",
                        distrito = "Lima",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.046374,
                        longitud = -77.042793,
                        imagenRes = R.drawable.estacion1
                    ),
                    Estacion(
                        nombre = "Estación Gamarra",
                        linea = "Línea 1",
                        distrito = "La Victoria",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.064722,
                        longitud = -77.014444,
                        imagenRes = R.drawable.estacion2
                    ),
                    Estacion(
                        nombre = "Estación San Martín",
                        linea = "Línea 1",
                        distrito = "Lima",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.051944,
                        longitud = -77.029722,
                        imagenRes = R.drawable.estacion3
                    )
                )

                estacionDao.insertAllEstaciones(estaciones)
            }
        }
    }
}