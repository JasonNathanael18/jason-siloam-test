package com.example.data.source.local.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.MealResponse

@Entity(tableName = MealResponseEntity.TABLE_NAME)
data class MealResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKeyId: Int? = null,
    val results: List<MealEntity>
) {

    fun toMealList(): MealResponse {
        return MealResponse(
            results = results.map { it.toMeal() }
        )
    }

    companion object {
        const val TABLE_NAME = "meal_response"
    }
}