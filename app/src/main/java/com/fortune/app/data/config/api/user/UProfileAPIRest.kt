package com.fortune.app.data.config.api.user

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.user.UProfileEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UProfileAPIRest {
    @GET("/user/getProfile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UProfileEntity>
}