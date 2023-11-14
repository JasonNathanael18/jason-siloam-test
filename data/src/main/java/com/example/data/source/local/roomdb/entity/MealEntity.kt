package com.example.data.source.local.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Meal

@Entity(tableName = MealEntity.TABLE_NAME)
data class MealEntity(
    @PrimaryKey val id: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strMealThumb: String,
    val strTags: String,
) {

    fun toMeal(): Meal {
        return Meal(
            strMeal = strMeal,
            strCategory = strCategory,
            strArea = strArea,
            strMealThumb = strMealThumb,
            strTags = strTags
        )
    }

    companion object {
        const val TABLE_NAME = "meal"
    }
}
