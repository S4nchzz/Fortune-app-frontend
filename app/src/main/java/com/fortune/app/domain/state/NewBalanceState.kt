package com.fortune.app.domain.state

sealed class NewBalanceState {
    data class Success(val balanceUpdated: Boolean): NewBalanceState()
    object Error: NewBalanceState()
}