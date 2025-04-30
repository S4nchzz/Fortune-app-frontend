package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.CardMovementModel

sealed class CardMovementState {
    data class Success(val cardMovementModel: List<CardMovementModel>): CardMovementState()
    object Error: CardMovementState()
}