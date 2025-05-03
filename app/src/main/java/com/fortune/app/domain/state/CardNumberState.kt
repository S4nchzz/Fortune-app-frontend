package com.fortune.app.domain.state

sealed class CardNumberState {
    data class Success(val number: String): CardNumberState()
    object Error: CardNumberState()
}