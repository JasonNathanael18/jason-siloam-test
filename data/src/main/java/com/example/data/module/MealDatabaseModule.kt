package com.example.data.module

import android.app.Application
import com.example.data.source.local.roomdb.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = MealDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideMealResponseDao(database: MealDatabase) =
        database.getMealResponseDao()

    @Singleton
    @Provides
    fun provideMealDao(database: MealDatabase) =
        database.getMealDao()
}