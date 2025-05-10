package com.fortune.app.domain.state

sealed class MakeBizumState {
    object Success: MakeBizumState()
    object UserNotFound: MakeBizumState()
    object Error: MakeBizumState()
}