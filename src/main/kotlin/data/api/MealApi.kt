package org.example.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MealApi {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val service: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }
}
