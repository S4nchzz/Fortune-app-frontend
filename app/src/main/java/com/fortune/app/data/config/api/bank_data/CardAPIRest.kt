package com.fortune.app.data.config.api.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.network.request.CardUUIDApiRequest
import com.fortune.app.network.response.CardNumberResponse
import com.fortune.app.network.response.card.LockCardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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
}