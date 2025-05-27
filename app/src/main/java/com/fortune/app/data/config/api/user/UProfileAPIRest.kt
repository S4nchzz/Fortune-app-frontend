package com.fortune.app.data.config.api.user

import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.network.request.account.UserIDRequest
import com.fortune.app.network.request.profile.UpdateProfileRequest
import com.fortune.app.network.response.account.PhoneResponse
import com.fortune.app.network.response.profile.ProfileToUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UProfileAPIRest {
    @GET("/user/getProfile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UProfileEntity>

    @GET("/user/getUpdateProfile")
    suspend fun getProfileToUpdate(@Header("Authorization") token: String): Response<ProfileToUpdateResponse>

    @POST("/user/updateProfile")
    suspend fun updateProfile(@Header("Authorization") token: String, @Body updateProfileRequest: UpdateProfileRequest): Response<Unit>

    @POST("/user/getUserPhone")
    suspend fun getPhone(@Header("Authorization") token: String, @Body userPhoneRequest: UserIDRequest): Response<PhoneResponse>

    @POST("/user/getProfileByUserID")
    suspend fun getProfileByUserID(@Header("Authorization") token: String, @Body userIDRequest: UserIDRequest): Response<UProfileEntity>
}