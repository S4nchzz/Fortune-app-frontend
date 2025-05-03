package com.fortune.app.domain.state

sealed class CardBalanceState {
    data class Success(val card_balance: Double): CardBalanceState()
    object Error: CardBalanceState()
}