package com.fortune.app.data.config.api.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.CardEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CardAPIRest {
    @GET("${BuildConfig.API_URL}/b_operations/card/findAccMainCard")
    suspend fun findMainCardByAccountID(@Query("accountId") accountId: String): Response<CardEntity>

    @GET("${BuildConfig.API_URL}/b_operations/card/findAccCards")
    suspend fun findAllCardsByAccId(@Query("accountId") accountId: String): Response<List<CardEntity>>
}