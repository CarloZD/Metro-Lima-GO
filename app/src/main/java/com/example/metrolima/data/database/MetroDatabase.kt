package com.example.metrolima.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.metrolima.R
import com.example.metrolima.data.dao.EstacionDao
import com.example.metrolima.data.dao.LineaDao
import com.example.metrolima.data.model.Estacion
import com.example.metrolima.data.model.Linea
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Estacion::class, Linea::class],
    version = 3, // ⚠️ Incrementa la versión
    exportSchema = false
)
abstract class MetroDatabase : RoomDatabase() {

    abstract fun estacionDao(): EstacionDao
    abstract fun lineaDao(): LineaDao

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
                    .fallbackToDestructiveMigration() // ⚠️ Temporal para desarrollo
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
                        populateDatabase(
                            database.estacionDao(),
                            database.lineaDao()
                        )
                    }
                }
            }

            suspend fun populateDatabase(
                estacionDao: EstacionDao,
                lineaDao: LineaDao
            ) {
                // Limpiar todo
                estacionDao.deleteAllEstaciones()
                lineaDao.deleteAllLineas()

                // ==================== LÍNEAS ====================
                val lineas = listOf(
                    Linea(
                        nombre = "Línea 1",
                        numero = 1,
                        colorHex = "#4CAF50", // Verde
                        estado = "Operativa",
                        descripcion = "Villa El Salvador - San Juan de Lurigancho",
                        estacionInicio = "Villa El Salvador",
                        estacionFin = "Bayóvar"
                    ),
                    Linea(
                        nombre = "Línea 2",
                        numero = 2,
                        colorHex = "#FFC107", // Amarillo
                        estado = "En construcción",
                        descripcion = "Ate - Callao",
                        estacionInicio = "Ate",
                        estacionFin = "Guardia Chalaca"
                    ),
                    Linea(
                        nombre = "Línea 3",
                        numero = 3,
                        colorHex = "#00BCD4", // Celeste
                        estado = "Planificada",
                        descripcion = "Comas - San Juan de Miraflores",
                        estacionInicio = "Comas",
                        estacionFin = "Angamos"
                    ),
                    Linea(
                        nombre = "Línea 4",
                        numero = 4,
                        colorHex = "#F44336", // Rojo
                        estado = "Planificada",
                        descripcion = "Aeropuerto - San Miguel",
                        estacionInicio = "Aeropuerto Jorge Chávez",
                        estacionFin = "La Marina"
                    ),
                    Linea(
                        nombre = "Línea 5",
                        numero = 5,
                        colorHex = "#E91E63", // Rosa
                        estado = "Planificada",
                        descripcion = "Miraflores - Chorrillos",
                        estacionInicio = "Benavides",
                        estacionFin = "Miguel Grau"
                    ),
                    Linea(
                        nombre = "Línea 6",
                        numero = 6,
                        colorHex = "#9C27B0", // Morado
                        estado = "Planificada",
                        descripcion = "Independencia - San Borja",
                        estacionInicio = "Túpac Amaru",
                        estacionFin = "Primavera"
                    )
                )

                lineaDao.insertAllLineas(lineas)

                // ==================== TODAS TUS ESTACIONES ORIGINALES ====================
                val estaciones = listOf(
                    // -------------------- LÍNEA 1 (Verde) --------------------
                    Estacion(
                        nombre = "Estación Villa El Salvador",
                        linea = "Línea 1",
                        distrito = "Villa El Salvador",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.204444,
                        longitud = -76.940694,
                        imagenRes = R.drawable.estacion3
                    ),
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
                        nombre = "Estación San Carlos",
                        linea = "Línea 1",
                        distrito = "San Juan de Lurigancho",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.9875,
                        longitud = -76.9950,
                        imagenRes = R.drawable.estacion5
                    ),
                    Estacion(
                        nombre = "Estación Los Jardines",
                        linea = "Línea 1",
                        distrito = "San Juan de Lurigancho",
                        horarioApertura = "05:30 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.981944,
                        longitud = -76.990833,
                        imagenRes = R.drawable.estacion4
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

                    // -------------------- LÍNEA 2 (Amarilla) --------------------
                    Estacion(
                        nombre = "Estación Ate",
                        linea = "Línea 2",
                        distrito = "Ate",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0430,
                        longitud = -76.9340,
                        imagenRes = R.drawable.estacion6
                    ),
                    Estacion(
                        nombre = "Estación Santa Anita",
                        linea = "Línea 2",
                        distrito = "Santa Anita",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0525,
                        longitud = -76.9719,
                        imagenRes = R.drawable.estacion7
                    ),
                    Estacion(
                        nombre = "Estación Nicolás Ayllón",
                        linea = "Línea 2",
                        distrito = "El Agustino",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0594,
                        longitud = -77.0015,
                        imagenRes = R.drawable.estacion8
                    ),
                    Estacion(
                        nombre = "Estación 28 de Julio",
                        linea = "Línea 2",
                        distrito = "Cercado de Lima",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0589,
                        longitud = -77.0311,
                        imagenRes = R.drawable.estacion9
                    ),
                    Estacion(
                        nombre = "Estación Guardia Chalaca",
                        linea = "Línea 2",
                        distrito = "Callao",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "09:30 PM",
                        latitud = -12.0521,
                        longitud = -77.1163,
                        imagenRes = R.drawable.estacion10
                    ),

                    // -------------------- LÍNEA 3 (Celeste) --------------------
                    Estacion(
                        nombre = "Estación Comas",
                        linea = "Línea 3",
                        distrito = "Comas",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.9469,
                        longitud = -77.0625,
                        imagenRes = R.drawable.estacion11
                    ),
                    Estacion(
                        nombre = "Estación Pizarro",
                        linea = "Línea 3",
                        distrito = "Cercado de Lima",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.0463,
                        longitud = -77.0382,
                        imagenRes = R.drawable.estacion10
                    ),
                    Estacion(
                        nombre = "Estación Angamos",
                        linea = "Línea 3",
                        distrito = "Surquillo",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.1123,
                        longitud = -77.0102,
                        imagenRes = R.drawable.estacion13
                    ),

                    // -------------------- LÍNEA 4 (Roja) --------------------
                    Estacion(
                        nombre = "Estación Aeropuerto Jorge Chávez",
                        linea = "Línea 4",
                        distrito = "Callao",
                        horarioApertura = "05:00 AM",
                        horarioCierre = "11:00 PM",
                        latitud = -12.0247,
                        longitud = -77.1128,
                        imagenRes = R.drawable.estacion14
                    ),
                    Estacion(
                        nombre = "Estación Elmer Faucett",
                        linea = "Línea 4",
                        distrito = "Callao",
                        horarioApertura = "05:00 AM",
                        horarioCierre = "11:00 PM",
                        latitud = -12.0350,
                        longitud = -77.0990,
                        imagenRes = R.drawable.estacion10
                    ),
                    Estacion(
                        nombre = "Estación La Marina",
                        linea = "Línea 4",
                        distrito = "San Miguel",
                        horarioApertura = "05:00 AM",
                        horarioCierre = "11:00 PM",
                        latitud = -12.0763,
                        longitud = -77.0857,
                        imagenRes = R.drawable.estacion10
                    ),

                    // -------------------- LÍNEA 5 (Rosada) --------------------
                    Estacion(
                        nombre = "Estación Benavides",
                        linea = "Línea 5",
                        distrito = "Miraflores",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:30 PM",
                        latitud = -12.1232,
                        longitud = -77.0155,
                        imagenRes = R.drawable.estacion17
                    ),
                    Estacion(
                        nombre = "Estación Miguel Grau",
                        linea = "Línea 5",
                        distrito = "Chorrillos",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:30 PM",
                        latitud = -12.1833,
                        longitud = -77.0178,
                        imagenRes = R.drawable.estacion18
                    ),

                    // -------------------- LÍNEA 6 (Morada) --------------------
                    Estacion(
                        nombre = "Estación Túpac Amaru",
                        linea = "Línea 6",
                        distrito = "Independencia",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -11.9890,
                        longitud = -77.0595,
                        imagenRes = R.drawable.estacion10
                    ),
                    Estacion(
                        nombre = "Estación Primavera",
                        linea = "Línea 6",
                        distrito = "San Borja",
                        horarioApertura = "06:00 AM",
                        horarioCierre = "10:00 PM",
                        latitud = -12.1054,
                        longitud = -76.9905,
                        imagenRes = R.drawable.estacion10
                    )
                )

                estacionDao.insertAllEstaciones(estaciones)
            }
        }
    }
}