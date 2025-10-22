package com.example.metrolima.data.network

import com.example.metrolima.data.model.EstacionRemota
import retrofit2.http.GET

interface MetroApiService {
    @GET("MetroLimaData.json") // o simplemente "." si el JSON está en la raíz
    suspend fun getEstacionesRemotas(): List<EstacionRemota>
}
