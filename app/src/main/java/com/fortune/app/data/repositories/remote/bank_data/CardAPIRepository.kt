package com.fortune.app.data.repositories.remote.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.remote.bank_data.CardAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CardAPIRepository @Inject constructor(
    val retrofit: Retrofit
) {
    private val cardAPIService = retrofit.create(CardAPIRest::class.java)

    suspend fun findMainCardByAccountID(accountId: String): CardEntity {
        return withContext(Dispatchers.IO) {
            cardAPIService.findMainCardByAccountID(accountId)
        }
    }

    suspend fun findAllCardsByAccId(accountId: String): List<CardEntity> {
        return withContext(Dispatchers.IO) {
            cardAPIService.findAllCardsByAccId(accountId)
        }
    }
}