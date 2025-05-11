package com.fortune.app.domain.state

import com.fortune.app.network.response.account.AccountBalanceResponse

sealed class AccountBalanceState {
    data class Success(val accountBalanceResponse: AccountBalanceResponse): AccountBalanceState()
    object Error: AccountBalanceState()
}