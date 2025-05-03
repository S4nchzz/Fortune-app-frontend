package com.fortune.app.domain.state

sealed class CardExpDateState {
    data class Success(val cardExpDate: String): CardExpDateState()
    object Error: CardExpDateState()
}