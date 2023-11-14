package com.example.data.module

import com.example.data.repository.MealRepositoryImpl
import com.example.data.source.local.roomdb.dao.MealDao
import com.example.data.source.local.roomdb.dao.MealResponseDao
import com.example.data.source.remote.MealApiService
import com.example.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealRepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepositoryImpl(
        mealApiService: MealApiService,
        mealResponseDao: MealResponseDao,
        mealDao: MealDao
    ): MealRepository = MealRepositoryImpl(mealApiService, mealResponseDao, mealDao)

}