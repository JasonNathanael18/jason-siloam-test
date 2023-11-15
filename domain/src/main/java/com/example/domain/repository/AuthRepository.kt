package com.example.domain.repository

import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun insertNewUserData(userName: String, password: String)
    fun getUserCredential(userName: String, password: String): Flow<Resource<Boolean>>
    fun checkUserNameAvailability(userName: String): Flow<Resource<Boolean>>
}