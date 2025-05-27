package com.fortune.app.data.config.api.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.network.request.movement.SimulatePaymentRequest
import com.fortune.app.network.response.account.AccountBalanceResponse
import com.fortune.app.network.response.account.AccountDataResponse
import com.fortune.app.network.response.account.FastContactResponse
import com.fortune.app.network.response.account.PaymentSimulationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountAPIRest {
    @POST("/b_operations/account/createAccount")
    suspend fun createAccount(@Header("Authorization") token: String): Response<Unit>

    @GET("/b_operations/account/findAccount")
    suspend fun findAccount(@Header("Authorization") token: String): Response<AccountEntity>

    @POST("/b_operations/movement/simulatePayment")
    suspend fun simulatePayment(@Header("Authorization") token: String, @Body simulatePaymentRequest: SimulatePaymentRequest): Response<PaymentSimulationResponse>

    @GET("/b_operations/account/getAccountBalance")
    suspend fun getAccountBalance(@Header("Authorization") token: String): Response<AccountBalanceResponse>

    @GET("/b_operations/account/getAccountData")
    suspend fun getAccountData(@Header("Authorization") token: String): Response<AccountDataResponse>

    @GET("/b_operations/account/getAccountMovements")
    suspend fun getAccountMovements(@Header("Authorization") token: String): Response<List<CardMovementEntity>>

    @GET("/b_operations/account/getFastContacts")
    suspend fun getFastContacts(@Header("Authorization") token: String): Response<List<FastContactResponse>>
}