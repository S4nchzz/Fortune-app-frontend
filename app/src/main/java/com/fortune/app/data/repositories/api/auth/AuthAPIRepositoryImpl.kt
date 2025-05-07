package com.fortune.app.data.repositories.api.auth

import android.util.Log
import com.fortune.app.data.config.api.auth.AuthAPIRest
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.repository.api.auth.AuthApiRepository
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import com.fortune.app.domain.state.RegisterState
import com.fortune.app.network.request.auth.CreateDigitalSignRequest
import com.fortune.app.network.request.auth.LoginRequest
import com.fortune.app.network.request.auth.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class AuthAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : AuthApiRepository {
    private val authApiService = retrofit.create(AuthAPIRest::class.java)

    override suspend fun register(userDTO: UserDTO, uProfileDTO: UProfileDTO): RegisterState {
        return withContext(Dispatchers.IO) {
            val response = authApiService.register(RegisterRequest(userDTO.identityDocument, userDTO.email, userDTO.password, uProfileDTO.name, uProfileDTO.phone, uProfileDTO.address))
            if (response.code() == 201) {
                RegisterState.Success
            } else if (response.code() == 409){
                RegisterState.UserAlreadyExistsError
            } else {
                RegisterState.UnexpectedError
            }
        }
    }

    override suspend fun login(identityDocument: String, password: String): LoginState {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApiService.login(LoginRequest(identityDocument, password))
                if (response.code() == 200 && response.body() != null) {
                    LoginState.Success(response.body()!!)
                } else {
                    LoginState.Error
                }
            } catch (e: Exception) {
                Log.e("LOGIN", "User credentials are incorrect", e)
                LoginState.Error
            }
        }
    }

    override suspend fun createDigitalSign(token: String, ds: Int): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = authApiService.createDigitalSign(token, CreateDigitalSignRequest(ds))

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }

    override suspend fun signOperation(token: String, ds: Int): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = authApiService.signOperation(token, CreateDigitalSignRequest(ds))

            if (response.code() == 200 && response.body() != null) {
                if (response.body()!!.operationAccepted) {
                    DefaultState.Success
                } else {
                    DefaultState.Error
                }
            } else {
                DefaultState.Error
            }
        }
    }
}