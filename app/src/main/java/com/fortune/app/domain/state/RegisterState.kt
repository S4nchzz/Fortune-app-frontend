package com.fortune.app.domain.state

sealed class RegisterState {
    object Success: RegisterState()
    object UnexpectedError: RegisterState()
    object UserAlreadyExistsError: RegisterState()
}