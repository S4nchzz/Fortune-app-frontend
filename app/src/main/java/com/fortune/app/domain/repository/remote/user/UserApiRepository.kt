package com.fortune.app.domain.repository.remote.user

import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.state.LoginState

interface UserApiRepository {
    suspend fun register(identity_document: String, email: String, password: String): UserModel
    suspend fun login(identityDocument: String, password: String): LoginState?
    suspend fun createDigitalSign(token: String, ds: Int): UserModel
}