package com.fortune.app.data.remote

import com.fortune.app.BuildConfig
import com.fortune.app.data.db.entities.UProfileEntity
import com.fortune.app.data.db.entities.UserEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UProfileAPIRest {
    @POST("${BuildConfig.API_URL}/userprofile/generateProfile")
    suspend fun userProfile(@Query("user_id") user_id: Long, @Query("name") name: String, @Query("address") address: String, @Query("phone") phone: String, @Query("online") online: Boolean): UProfileEntity // Online property false by default

    @GET("${BuildConfig.API_URL}/userprofile/findUserByProfileId")
    suspend fun findProfileByUserId(@Query("user_id") user_id: Long): UProfileEntity
}