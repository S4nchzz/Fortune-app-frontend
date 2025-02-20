package com.fortune.app.data.remote

import com.fortune.app.BuildConfig
import com.fortune.app.data.db.entities.UserEntity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPIRest {
    @GET("${BuildConfig.API_URL}/user/login")
    suspend fun login(@Query("identityDocument") identityDocument: String, @Query("password") password: String): UserEntity

    @POST("${BuildConfig.API_URL}/user/register")
    suspend fun register(@Query("identityDocument") identityDocument: String, @Query("email") email: String, @Query("password") password: String): UserEntity

    @POST("${BuildConfig.API_URL}/user/createDigitalSign")
    suspend fun createDigitalSign(@Query("user_id") user_id: Long, @Query("digital_sign") ds: Int): UserEntity

    @POST("${BuildConfig.API_URL}/user/updateProfileStatus")
    suspend fun updateProfileStatus(@Query("user_id") id: Long, @Query("is_profile_created") profileCreated: Boolean): UserEntity
}