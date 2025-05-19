package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.CardModel

sealed class CardUniqueState {
    data class Success(val cardModel: CardModel): CardUniqueState()
    object Error: CardUniqueState()
}