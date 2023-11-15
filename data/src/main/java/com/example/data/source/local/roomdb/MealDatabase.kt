package com.example.data.source.local.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.source.local.roomdb.converter.MealResponseEntityConverter
import com.example.data.source.local.roomdb.dao.MealDao
import com.example.data.source.local.roomdb.dao.MealResponseDao
import com.example.data.source.local.roomdb.dao.auth.AuthDao
import com.example.data.source.local.roomdb.entity.MealEntity
import com.example.data.source.local.roomdb.entity.MealResponseEntity
import com.example.data.source.local.roomdb.entity.local.AuthEntity

@Database(
    entities = [MealResponseEntity::class, MealEntity::class, AuthEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MealResponseEntityConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun getMealDao(): MealDao

    abstract fun getMealResponseDao(): MealResponseDao

    abstract fun getAuthDao(): AuthDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getDatabase(context: Context): MealDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}