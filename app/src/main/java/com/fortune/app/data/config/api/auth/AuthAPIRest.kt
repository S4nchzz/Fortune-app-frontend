package com.fortune.app.data.config.api.auth

import com.fortune.app.BuildConfig
import com.fortune.app.network.response.auth.LoginResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPIRest {
    @GET("/auth/login")
    suspend fun login(@Query("identityDocument") identityDocument: String, @Query("password") password: String): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Query("identityDocument") identityDocument: String, @Query("email") email: String, @Query("password") password: String, @Query("name") name: String, @Query("phone") phone: String, @Query("address") address: String): Response<Unit>

    @POST("/user/createDigitalSign")
    suspend fun createDigitalSign(@Header("Authorization") token: String, @Query("digital_sign") ds: Int): Response<Unit>
}