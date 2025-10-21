package com.example.metrolima.data.network

import com.example.metrolima.data.model.EstacionRemota
import retrofit2.http.GET

interface MetroApiService {
    @GET("MetroLimaData.json")
    suspend fun getEstacionesRemotas(): List<EstacionRemota>
}
