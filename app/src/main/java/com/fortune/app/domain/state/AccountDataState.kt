package com.fortune.app.domain.state

sealed class AccountDataState {
    data class Success(val accountID: String, val accountBalance: Double): AccountDataState()
    object Error: AccountDataState()
}
