package com.fortune.app.domain.repository.api.bank_Data

import com.fortune.app.domain.state.CardBalanceState
import com.fortune.app.domain.state.CardCvvState
import com.fortune.app.domain.state.CardExpDateState
import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.CardNumberState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState
import com.fortune.app.domain.state.NewBalanceState

interface CardRepository {
    suspend fun findCards(token: String): CardState
    suspend fun findMovements(token: String, card_uuid: String): CardMovementState
    suspend fun lockCard(token: String, cardUuid: String): LockCardState
    suspend fun isCardLocked(token: String, cardUuid: String): LockCardState
    suspend fun getCardNumber(token: String, card_uuid: String): CardNumberState
    suspend fun getExpDate(token: String, card_uuid: String): CardExpDateState
    suspend fun getCvv(token: String, cardUuid: String): CardCvvState
    suspend fun getCardBalance(s: String, cardUuid: String): CardBalanceState
    suspend fun addNewBalance(newBalance: Double, s: String): NewBalanceState
    suspend fun addNewCard(token: String): DefaultState
}