package com.fortune.app.domain.state

sealed class LoginState {
    object Error: LoginState()
    data class Success(val token: String): LoginState()
}