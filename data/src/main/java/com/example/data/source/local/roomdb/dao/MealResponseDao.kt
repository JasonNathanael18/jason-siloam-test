package com.example.data.source.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.roomdb.entity.MealResponseEntity

@Dao
interface MealResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealResponse(movies: MealResponseEntity)

    @Query("SELECT * FROM ${MealResponseEntity.TABLE_NAME}")
    suspend fun getMealResponse(): List<MealResponseEntity>

    @Query("DELETE FROM ${MealResponseEntity.TABLE_NAME}")
    suspend fun deleteAll()
}