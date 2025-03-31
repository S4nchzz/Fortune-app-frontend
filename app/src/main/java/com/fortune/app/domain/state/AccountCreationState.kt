package com.fortune.app.domain.state

sealed class AccountCreationState {
    object Error: AccountCreationState()
    object Success: AccountCreationState()
}