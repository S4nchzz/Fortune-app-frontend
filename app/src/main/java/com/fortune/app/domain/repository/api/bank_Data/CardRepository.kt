package com.fortune.app.domain.repository.api.bank_Data

import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState

interface CardRepository {
    suspend fun findCards(token: String): CardState
    suspend fun findMovements(token: String, card_uuid: String): CardMovementState
    abstract suspend fun lockCard(s: String, cardUuid: String): LockCardState
}