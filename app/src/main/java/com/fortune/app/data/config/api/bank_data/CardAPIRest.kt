package com.fortune.app.data.config.api.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.network.request.CardMovementRequest
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
    suspend fun findMovements(@Header("Authorization") token: String, @Body card_uuid: CardMovementRequest): Response<List<CardMovementEntity>>
}