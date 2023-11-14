package com.example.navigation

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataConvert @Inject constructor() {

    inline fun <reified T> toData(data: String) : T? {
        if ( data.isEmpty()) {
            return null
        }
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.fromJson(data)
    }

    inline fun <reified T> toJson(data: T) : String? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.toJson(data)
    }
}