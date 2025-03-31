package com.fortune.app.data.repositories.api.user

import android.util.Log
import com.fortune.app.data.mapper.user.UserMapper
import com.fortune.app.data.config.api.user.UserAPIRest
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.repository.api.user.UserApiRepository
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UserAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : UserApiRepository {
    private val userAPIService = retrofit.create(UserAPIRest::class.java)

    override suspend fun register(userDTO: UserDTO, uProfileDTO: UProfileDTO): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = userAPIService.register(userDTO.identityDocument, userDTO.email, userDTO.password, uProfileDTO.name, uProfileDTO.phone, uProfileDTO.address)
            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }

    override suspend fun login(identityDocument: String, password: String): LoginState {
        return withContext(Dispatchers.IO) {
            try {
                val response = userAPIService.login(identityDocument, password)
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
            val response = userAPIService.createDigitalSign(token, ds)

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }
}