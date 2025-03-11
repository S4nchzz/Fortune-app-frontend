package com.fortune.app.data.config.remote.bank_data

import com.fortune.app.BuildConfig
import com.fortune.app.data.entities.bank_data.CardEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CardAPIRest {
    @GET("${BuildConfig.API_URL}/b_operations/card/findAccMainCard")
    suspend fun findMainCardByAccountID(@Query("accountId") accountId: String): CardEntity

    @GET("${BuildConfig.API_URL}/b_operations/card/findAccCards")
    suspend fun findAllCardsByAccId(@Query("accountId") accountId: String): List<CardEntity>
}