package com.fortune.app.domain.repository.api.user

import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import com.fortune.app.network.response.api.ApiResponseData
import retrofit2.Response

interface UserApiRepository {
    suspend fun register(userDTO: UserDTO, uProfileDTO: UProfileDTO): DefaultState
    suspend fun login(identityDocument: String, password: String): LoginState?
    suspend fun createDigitalSign(token: String, ds: Int): DefaultState
}