package com.example.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavModule {
    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return Navigator()
    }
}