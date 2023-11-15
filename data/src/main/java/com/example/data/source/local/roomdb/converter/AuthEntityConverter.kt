package com.example.data.source.local.roomdb.converter

import com.example.data.source.local.roomdb.entity.local.AuthEntity

object AuthEntityConverter {

    fun toAuthEntity(
        userName: String,
        password: String
    ): AuthEntity {
        return AuthEntity(
            userName = userName,
            password = password
        )
    }

}