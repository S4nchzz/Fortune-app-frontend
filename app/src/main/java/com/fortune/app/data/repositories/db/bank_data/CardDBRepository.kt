package com.fortune.app.data.repositories.db.bank_data

import android.util.Log
import com.fortune.app.data.db.AppDatabase
import com.fortune.app.data.entities.bank_data.CardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardDBRepository @Inject constructor(
    val appDatabase: AppDatabase
) {
    suspend fun saveCards(cardAPIEntity: List<CardEntity>) {
        withContext(Dispatchers.IO) {
            appDatabase.cardDao().saveCards(cardAPIEntity)
        }
    }

    suspend fun saveCard(cardAPIEntity: CardEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.cardDao().saveCard(cardAPIEntity)
        }
    }

    suspend fun clearLocalCardData() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.cardDao().clearLocalCards()
            } catch (e: Exception) {
                Log.e("LocalRemoveCards", "Unnable to remove cards, field empties")
            }
        }
    }
}