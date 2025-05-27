package com.fortune.app.domain.state

sealed class UserPhoneState {
    data class Success(
        val phone: String
    ): UserPhoneState()

    object Error: UserPhoneState()
}