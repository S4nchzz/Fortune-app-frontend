package com.fortune.app.data.repositories.remote.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMapper
import com.fortune.app.data.config.remote.bank_data.CardAPIRest
import com.fortune.app.domain.model.bank_data.CardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CardAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
) {
    private val cardAPIService = retrofit.create(CardAPIRest::class.java)

    suspend fun findMainCardByAccountID(accountId: String): CardModel {
        return withContext(Dispatchers.IO) {
            CardMapper.mapToDomain(cardAPIService.findMainCardByAccountID(accountId))
        }
    }

    suspend fun findAllCardsByAccId(accountId: String): List<CardModel> {
        return withContext(Dispatchers.IO) {
            val cardEntities = cardAPIService.findAllCardsByAccId(accountId)

            val cardModelList: MutableList<CardModel> = mutableListOf()
            cardEntities.forEach { item ->
                cardModelList.add(CardMapper.mapToDomain(item))
            }

            cardModelList
        }
    }
}