package com.example.whatrubbish.util

import com.example.whatrubbish.Bus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
//    https://blog.csdn.net/why12345678901/article/details/115483426
//    private const val BASE_URL = "https://www.wanandroid.com"
    private const val BASE_URL = Bus.baseDbUrl
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()
    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)
    inline fun <reified T> create():T = create(T::class.java)
}
