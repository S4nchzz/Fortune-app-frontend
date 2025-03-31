package com.fortune.app.data.config.api.user

import com.fortune.app.BuildConfig
import com.fortune.app.network.response.api.ApiResponseData
import com.fortune.app.network.response.auth.LoginResponse
import com.fortune.app.data.entities.user.UserEntity
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPIRest {
    @GET("${BuildConfig.API_URL}/auth/login")
    suspend fun login(@Query("identityDocument") identityDocument: String, @Query("password") password: String): Response<LoginResponse>

    @POST("${BuildConfig.API_URL}/auth/register")
    suspend fun register(@Query("identityDocument") identityDocument: String, @Query("email") email: String, @Query("password") password: String, @Query("name") name: String, @Query("phone") phone: String, @Query("address") address: String): Response<Unit>

    @POST("${BuildConfig.API_URL}/user/createDigitalSign")  
    suspend fun createDigitalSign(@Header("Authorization") token: String, @Query("digital_sign") ds: Int): Response<Unit>
}