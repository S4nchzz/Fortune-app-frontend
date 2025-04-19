package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.CardModel

sealed class CardState {
    data class Success(val cards: List<CardModel>): CardState()
    object Error: CardState()
}