package com.fortune.app.data.config.api.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.network.request.movement.SimulatePaymentRequest
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
}