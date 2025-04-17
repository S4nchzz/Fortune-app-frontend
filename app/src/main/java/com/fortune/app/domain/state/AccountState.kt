package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.AccountModel

sealed class AccountState {
    data class Success(val accountModel: AccountModel): AccountState()
    object Error: AccountState()
}