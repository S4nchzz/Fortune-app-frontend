package com.fortune.app.data.repositories.api.user

import com.fortune.app.data.config.api.auth.AuthAPIRest
import com.fortune.app.data.config.api.user.UserAPIRest
import com.fortune.app.domain.repository.api.user.UserAPIRepository
import com.fortune.app.domain.state.UserDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UserAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
): UserAPIRepository {
    private val userAPIService = retrofit.create(UserAPIRest::class.java)

    override suspend fun getUserData(): UserDataState {
        return withContext(Dispatchers.IO) {
            val x = userAPIService.getMainClientData()
            // comprobar si la llamada ha funcionado y si retornar un UserDataState succes con los datos del body
            UserDataState.Success(x.body()!!) // MAL
        }
    }
}