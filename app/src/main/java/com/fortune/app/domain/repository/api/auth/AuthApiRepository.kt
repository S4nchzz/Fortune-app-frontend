package com.fortune.app.domain.repository.api.auth

import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import com.fortune.app.domain.state.RegisterState

interface AuthApiRepository {
    suspend fun register(userDTO: UserDTO, uProfileDTO: UProfileDTO): RegisterState
    suspend fun login(identityDocument: String, password: String): LoginState?
    suspend fun createDigitalSign(token: String, ds: Int): DefaultState
}