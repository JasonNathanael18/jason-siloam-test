package com.example.data.module

import com.example.domain.repository.MealRepository
import com.example.domain.usecase.GetMeal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealUseCaseModule {

    @Provides
    @Singleton
    fun provideGetMealUseCase(repository: MealRepository): GetMeal =
        GetMeal(repository)
}