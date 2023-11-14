package com.example.data.source.remote.dto

import com.example.data.source.local.roomdb.entity.MealResponseEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResponseDto(
    @Json(name = "meals")
    val results: List<MealDto>? = listOf(),
) {
    fun toMealResponseEntity(): MealResponseEntity? {
        return results?.let { mealDtos ->
            MealResponseEntity(
                results = mealDtos.map { it.toMealEntity() }
            )
        }
    }
}