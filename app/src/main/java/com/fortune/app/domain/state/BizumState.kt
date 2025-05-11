package com.fortune.app.domain.state

sealed class BizumState {
    object Success: BizumState()
    object UserNotFound: BizumState()
    object Error: BizumState()
}