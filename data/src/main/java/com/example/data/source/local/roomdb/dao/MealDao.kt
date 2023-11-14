package com.example.data.source.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.roomdb.entity.MealEntity

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealList(movies: List<MealEntity>)

    @Query("SELECT * FROM ${MealEntity.TABLE_NAME}")
    suspend fun getMealList(): List<MealEntity>

    @Query("DELETE FROM ${MealEntity.TABLE_NAME}")
    suspend fun deleteAll()
}