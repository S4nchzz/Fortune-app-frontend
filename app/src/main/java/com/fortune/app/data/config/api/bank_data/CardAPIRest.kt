package com.fortune.app.data.config.api.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.network.request.account.CardUpdateAccBalanceRequest
import com.fortune.app.network.request.card.CardUUIDApiRequest
import com.fortune.app.network.response.account.NewBalanceResponse
import com.fortune.app.network.response.card.CardBalanceResponse
import com.fortune.app.network.response.card.CardCvvResponse
import com.fortune.app.network.response.card.CardExpDateResponse
import com.fortune.app.network.response.card.CardNumberResponse
import com.fortune.app.network.response.card.LockCardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CardAPIRest {
    @GET("/b_operations/card/findCards")
    suspend fun findCards(@Header("Authorization") token: String): Response<List<CardEntity>>

    @POST("/b_operations/movement/findMovements")
    suspend fun findMovements(@Header("Authorization") token: String, @Body card_uuid: CardUUIDApiRequest): Response<List<CardMovementEntity>>

    @POST("/b_operations/card/manageCardLock")
    suspend fun lockCard(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<LockCardResponse>

    @POST("/b_operations/card/isCardLocked")
    suspend fun isCardLocked(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<LockCardResponse>

    @POST("/b_operations/card/getCardNumber")
    suspend fun getCardNumber(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<CardNumberResponse>

    @POST("/b_operations/card/getExpDate")
    suspend fun getExpDate(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<CardExpDateResponse>

    @POST("/b_operations/card/getCvv")
    suspend fun getCvv(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<CardCvvResponse>

    @POST("/b_operations/card/getBalance")
    suspend fun getBalance(@Header("Authorization") token: String, @Body cardUUIDApiRequest: CardUUIDApiRequest): Response<CardBalanceResponse>

    @POST("/b_operations/card/addAccountBalance")
    suspend fun addNewBalance(@Header("Authorization") token: String, @Body newBalance: CardUpdateAccBalanceRequest): Response<NewBalanceResponse>

    @GET("/b_operations/account/addNewCard")
    suspend fun addNewCard(@Header("Authorization") token: String): Response<Unit>
}