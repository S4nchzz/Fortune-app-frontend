package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.CardMovementModel

sealed class CardMovementState {
    object Empty: CardMovementState()
    data class Success(val cardMovementModel: List<CardMovementModel>): CardMovementState()
    object Error: CardMovementState()
}