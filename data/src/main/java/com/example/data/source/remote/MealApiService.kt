package com.example.data.source.remote

import com.example.data.source.remote.dto.MealResponseDto
import retrofit2.http.GET

interface MealApiService {
    @GET("v1/1/search.php?f=b")
    suspend fun getMealList(): MealResponseDto
}