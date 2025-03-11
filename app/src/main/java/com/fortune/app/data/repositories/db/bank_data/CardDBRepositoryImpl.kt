package com.fortune.app.data.repositories.db.bank_data

import android.util.Log
import com.fortune.app.data.config.db.AppDatabase
import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMapper
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.repository.db.bank_Data.CardDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardDBRepositoryImpl @Inject constructor(
    val appDatabase: AppDatabase
) : CardDBRepository {
    override suspend fun saveCards(cardApiModels: List<CardModel>) {
        withContext(Dispatchers.IO) {
            val mappedCards: MutableList<CardEntity> = mutableListOf()
            cardApiModels.forEach { item ->
                mappedCards.add(CardMapper.mapToEntity(item))
            }

            appDatabase.cardDao().saveCards(mappedCards)
        }
    }

    override suspend fun saveCard(cardApiModel: CardModel) {
        withContext(Dispatchers.IO) {
            appDatabase.cardDao().saveCard(CardMapper.mapToEntity(cardApiModel))
        }
    }

    override suspend fun clearLocalCardData() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.cardDao().clearLocalCards()
            } catch (e: Exception) {
                Log.e("LocalRemoveCards", "Unnable to remove cards, field empties")
            }
        }
    }
}