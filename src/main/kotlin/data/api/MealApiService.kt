package org.example.data.api

import org.example.domain.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun getMealByName(@Query("s") name: String): MealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse
}
