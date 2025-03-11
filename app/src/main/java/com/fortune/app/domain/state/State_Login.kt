package com.fortune.app.domain.state

import com.fortune.app.domain.model.user.UserModel

sealed class State_Login {
    object Error: State_Login()
    data class Success(val userModel: UserModel): State_Login()
}