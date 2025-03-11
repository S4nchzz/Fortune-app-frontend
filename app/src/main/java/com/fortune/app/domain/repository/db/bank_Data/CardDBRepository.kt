package com.fortune.app.domain.repository.db.bank_Data

import com.fortune.app.domain.model.bank_data.CardModel

interface CardDBRepository {
    suspend fun saveCards(cardAPIEntity: List<CardModel>)
    suspend fun saveCard(cardAPIEntity: CardModel)
    suspend fun clearLocalCardData()
}