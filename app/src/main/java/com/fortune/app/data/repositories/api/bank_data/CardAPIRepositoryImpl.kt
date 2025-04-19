package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.CardMapper
import com.fortune.app.data.config.api.bank_data.CardAPIRest
import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.repository.api.bank_Data.CardRepository
import com.fortune.app.domain.state.CardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CardAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
): CardRepository {
    private val cardAPIService = retrofit.create(CardAPIRest::class.java)

    suspend fun findCards(token: String): CardState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.findCards(token)

            if (response.code() == 200 && response.body() != null) {
                val domainCards: MutableList<CardModel> = mutableListOf()
                response.body()!!.forEach { cardEntity: CardEntity ->
                    domainCards.add(CardMapper.mapToDomain(cardEntity))
                }

                CardState.Success(domainCards)
            } else {
                CardState.Error
            }
        }
    }
}