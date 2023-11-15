package com.example.domain.usecase

import com.example.domain.repository.AuthRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class AuthUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun insertNewUserCredential(userName: String, password: String) {
        authRepository.insertNewUserData(userName, password)
    }

    fun checkIfUserCredentialExist(userName: String, password: String): Flow<Resource<Boolean>> {
        return authRepository.getUserCredential(userName, password)
    }

    fun checkIfUserNameAvailable(userName: String): Flow<Resource<Boolean>> {
        return authRepository.checkUserNameAvailability(userName)
    }
}