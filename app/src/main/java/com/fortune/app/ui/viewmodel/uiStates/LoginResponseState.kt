package com.fortune.app.ui.viewmodel.uiStates

import com.fortune.app.data.entities.user.UserEntity

sealed class LoginResponseState {
    object Error: LoginResponseState()
    data class Success(val userEntity: UserEntity): LoginResponseState()
}