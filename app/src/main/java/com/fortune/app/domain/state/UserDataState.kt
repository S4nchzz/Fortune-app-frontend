package com.fortune.app.domain.state

import com.fortune.app.network.response.user.UserDataResponse

sealed class UserDataState {
    data class Success(val userDataResponse: UserDataResponse): UserDataState()
    object Error: UserDataState()
}