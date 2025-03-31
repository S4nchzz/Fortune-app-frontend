package com.fortune.app.domain.state

sealed class DefaultState {
    object Error: DefaultState()
    object Success: DefaultState()
}