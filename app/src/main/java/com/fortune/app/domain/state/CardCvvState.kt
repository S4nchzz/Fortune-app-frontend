package com.fortune.app.domain.state

sealed class CardCvvState {
    data class Success(val cardCvv: Int): CardCvvState()
    object Error: CardCvvState()
}