package com.fortune.app.domain.repository.api.bank_Data

import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.CardState

interface CardRepository {
    suspend fun findCards(token: String): CardState
    suspend fun findMovements(token: String, card_uuid: String): CardMovementState
}