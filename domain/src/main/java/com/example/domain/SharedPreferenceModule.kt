package com.example.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceModule @Inject constructor(@ApplicationContext context : Context){
    val prefs = context.getSharedPreferences("AUTH_PREF",Context.MODE_PRIVATE)
    var editor = prefs.edit()

    fun getLoginStatus(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }
    fun setLoginStatus(isLogin: Boolean) {
        editor.putBoolean(IS_LOGIN, isLogin).apply()
    }

    companion object{
        const val IS_LOGIN = "IS_LOGIN"
    }
}