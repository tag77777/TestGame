package com.m77777_888.myapplication.api

import com.m77777_888.myapplication.START_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface BaseAPI {

    @GET(START_ENDPOINT)
    suspend fun getReference(): Response<String>
}