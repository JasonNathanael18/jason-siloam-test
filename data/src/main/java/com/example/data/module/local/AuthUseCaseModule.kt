package com.example.data.module.local

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MealRepository
import com.example.domain.usecase.AuthUseCase
import com.example.domain.usecase.GetMeal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAuthUseCase(repository: AuthRepository): AuthUseCase =
        AuthUseCase(repository)
}