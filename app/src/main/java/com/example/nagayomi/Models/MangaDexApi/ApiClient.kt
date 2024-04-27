package com.example.nagayomi.Models.MangaDexApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.mangadex.org"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: MangaDexApi by lazy {
        RetrofitClient.retrofit.create(MangaDexApi::class.java)
    }
}