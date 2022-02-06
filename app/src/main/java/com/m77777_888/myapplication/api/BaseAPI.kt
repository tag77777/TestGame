package com.m77777_888.myapplication.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface BaseAPI {

    @GET
    suspend fun get(): String
}