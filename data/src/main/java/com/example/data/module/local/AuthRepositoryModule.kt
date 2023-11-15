package com.example.data.module.local

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.source.local.roomdb.dao.auth.AuthDao
import com.example.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        authDao: AuthDao
    ): AuthRepository = AuthRepositoryImpl(authDao)

}