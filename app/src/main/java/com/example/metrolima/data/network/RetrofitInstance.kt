package com.example.metrolima.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL =
        "https://gist.githubusercontent.com/mmaffer/4e81d10920d950cf6e7f3b1d94bee476/raw/" // 🔹 deja solo el prefijo, no incluyas el nombre del archivo

    val api: MetroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetroApiService::class.java)
    }
}
