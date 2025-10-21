package com.example.metrolima.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL =
        "https://gist.githubusercontent.com/mmaffer/4e81d10920d950cf6e7f3b1d94bee476/raw/55bafa8d6cee5630309749f19e55a05ad08bff98/"

    val api: MetroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // termina en "/"
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetroApiService::class.java)
    }
}
