package com.m77777_888.myapplication.api

import com.m77777_888.myapplication.START_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object ReferenceRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(START_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    val api: BaseAPI by lazy {
        retrofit.create(BaseAPI::class.java)
    }

}