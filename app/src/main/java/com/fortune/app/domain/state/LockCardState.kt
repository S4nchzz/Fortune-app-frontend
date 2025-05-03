package com.fortune.app.domain.state

sealed class LockCardState {
    data class Success(val locked: Boolean): LockCardState()
    object Error: LockCardState()
}