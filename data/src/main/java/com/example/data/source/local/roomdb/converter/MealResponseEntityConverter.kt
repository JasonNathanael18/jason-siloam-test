package com.example.data.source.local.roomdb.converter

import androidx.room.TypeConverter
import com.example.data.source.local.roomdb.entity.MealEntity
import com.example.domain.model.Meal
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MealResponseEntityConverter {

    @TypeConverter
    fun fromStringToMovieList(value: String): List<Meal>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<Meal>>(Types.newParameterizedType(List::class.java, Meal::class.java))
            .fromJson(value)

    @TypeConverter
    fun fromMovieListTypeToString(movieListType: List<Meal>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<Meal>>(Types.newParameterizedType(List::class.java, Meal::class.java))
            .toJson(movieListType)


    @TypeConverter
    fun fromStringToMovieEntityList(value: String): List<MealEntity>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<MealEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    MealEntity::class.java
                )
            ).fromJson(value)

    @TypeConverter
    fun fromMovieEntityListTypeToString(mealEntityListType: List<MealEntity>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<MealEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    MealEntity::class.java
                )
            ).toJson(mealEntityListType)
}