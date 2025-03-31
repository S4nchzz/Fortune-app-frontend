package com.fortune.app.data.config.api.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.AccountEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountAPIRest {
    @POST("${BuildConfig.API_URL}/b_operations/account/createAccount")
    suspend fun createAccount(@Header("Authorization") token: String): Response<Unit>

    @GET("${BuildConfig.API_URL}/b_operations/account/findAccount")
    suspend fun findAccountByUserId(@Header("Authorization") token: String): AccountEntity
}