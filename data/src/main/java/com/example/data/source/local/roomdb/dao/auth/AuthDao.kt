package com.example.data.source.local.roomdb.dao.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.roomdb.entity.local.AuthEntity

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUserCredential(auth: AuthEntity)

    @Query("SELECT * FROM ${AuthEntity.TABLE_NAME} WHERE userName = :userName AND password = :password")
    suspend fun getAuthListByUserNameAndPassword(userName: String, password: String): List<AuthEntity>

    @Query("SELECT * FROM ${AuthEntity.TABLE_NAME} WHERE userName = :userName")
    suspend fun getAuthListByUserName(userName: String): List<AuthEntity>

//    @Query("DELETE FROM ${AuthEntity.TABLE_NAME}")
//    suspend fun deleteAll()
}