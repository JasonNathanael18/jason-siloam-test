package com.example.data.repository

import com.example.data.source.local.roomdb.converter.AuthEntityConverter
import com.example.data.source.local.roomdb.dao.auth.AuthDao
import com.example.domain.repository.AuthRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import java.util.Base64

class AuthRepositoryImpl @Inject constructor(
    private val authDao: AuthDao
) : AuthRepository {
    override suspend fun insertNewUserData(userName: String, password: String) {
        val encoded = Base64.getEncoder().encodeToString(password.toByteArray())
        val authEntity = AuthEntityConverter.toAuthEntity(userName, encoded)
        authDao.insertNewUserCredential(authEntity)
    }

    override fun getUserCredential(userName: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val encoded = Base64.getEncoder().encodeToString(password.toByteArray())
        val auth = authDao.getAuthListByUserNameAndPassword(userName, encoded)

        if (auth.isEmpty()) {
            emit(Resource.Success(false))
        } else {
            emit(Resource.Success(true))
        }
    }

    override fun checkUserNameAvailability(userName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val auth = authDao.getAuthListByUserName(userName)

        if (auth.isEmpty()) {
            emit(Resource.Success(true))
        } else {
            emit(Resource.Success(false))
        }
    }
}