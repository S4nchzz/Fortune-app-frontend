package com.fortune.app.data.config.api.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CardAPIRest {
    @GET("/b_operations/card/findCards")
    suspend fun findCards(@Header("Authorization") token: String): Response<List<CardEntity>>
}