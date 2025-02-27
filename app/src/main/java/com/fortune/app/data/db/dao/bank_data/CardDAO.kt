package com.fortune.app.data.db.dao.bank_data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fortune.app.data.entities.bank_data.CardEntity

@Dao
abstract class CardDAO {
    @Insert // INSERT SOME CARDS
    abstract fun saveCards(cardAPIEntity: List<CardEntity>)

    @Insert // INSERT ONE CARD (MAIN CARD)
    abstract fun saveCard(cardAPIEntity: CardEntity)

    @Query("DELETE FROM fl_card_data")
    abstract fun clearLocalCards()
}