package com.fortune.app.data.remote.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.AccountEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountAPIRest {
    @POST("${BuildConfig.API_URL}/b_operations/account/createAccount")
    suspend fun createAccount(@Query("user_id") user_id: Long): AccountEntity

    @GET("${BuildConfig.API_URL}/b_operations/account/findAccount")
    suspend fun findAccountByUserId(@Query("user_id") userId: Long): AccountEntity
}