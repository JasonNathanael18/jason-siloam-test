package com.example.data.source.local.roomdb.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AuthEntity.TABLE_NAME)
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKeyId: Int? = null,
    val userName: String,
    val password: String
) {
//    fun toMeal(): Meal {
//        return Meal(
//            idMeal = id.toString(),
//            strMeal = strMeal,
//            strCategory = strCategory,
//            strArea = strArea,
//            strMealThumb = strMealThumb,
//            strTags = strTags
//        )
//    }

    companion object {
        const val TABLE_NAME = "auth"
    }
}