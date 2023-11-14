package com.example.data.source.remote

import com.example.data.source.remote.dto.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("v1/1/search.php")
    suspend fun getMealList(
        @Query("f") query: String
    ): MealResponseDto
}