package com.fortune.app.data.config.api.user

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.user.UProfileEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UProfileAPIRest {
    @POST("${BuildConfig.API_URL}/userprofile/generateProfile")
    suspend fun userProfile(@Header("Authorization") token: String, @Query("name") name: String, @Query("address") address: String, @Query("phone") phone: String, @Query("online") online: Boolean): UProfileEntity // Online property false by default

    @GET("${BuildConfig.API_URL}/userprofile/findUserByProfileId")
    suspend fun findProfileByUserId(@Query("user_id") user_id: Long): UProfileEntity
}