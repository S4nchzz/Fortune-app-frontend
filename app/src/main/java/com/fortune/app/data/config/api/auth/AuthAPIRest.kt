package com.fortune.app.data.config.api.auth

import com.fortune.app.network.request.auth.ChangeDigitalSignRequest
import com.fortune.app.network.request.auth.ChangePasswordRequest
import com.fortune.app.network.request.auth.CreateDigitalSignRequest
import com.fortune.app.network.request.auth.LoginRequest
import com.fortune.app.network.request.auth.RegisterRequest
import com.fortune.app.network.response.auth.AuthDSOperation
import com.fortune.app.network.response.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPIRest {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>

    @POST("/user/createDigitalSign")
    suspend fun createDigitalSign(@Header("Authorization") token: String, @Body createDigitalSignRequest: CreateDigitalSignRequest): Response<Unit>

    @POST("/auth/signOperation")
    suspend fun signOperation(@Header("Authorization") token: String, @Body createDigitalSignRequest: CreateDigitalSignRequest): Response<AuthDSOperation>

    @POST("/auth/changePassword")
    suspend fun changePassword(@Header("Authorization") tokentoken: String, @Body changePasswordRequest: ChangePasswordRequest): Response<Unit>

    @POST("/auth/changeDigitalSign")
    suspend fun resetDigitalSign(@Header("Authorization") token: String, @Body changeDigitalSignRequest: ChangeDigitalSignRequest): Response<Unit>
}