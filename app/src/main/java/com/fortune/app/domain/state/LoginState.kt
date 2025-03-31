package com.fortune.app.domain.state

import com.fortune.app.network.response.api.ApiResponseData
import com.fortune.app.network.response.auth.LoginResponse

sealed class LoginState {
    object Error: LoginState()
    data class Success(val responseInfo: LoginResponse): LoginState()
}