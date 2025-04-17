package com.fortune.app.data.repositories.api.user

import com.fortune.app.data.config.api.user.UProfileAPIRest
import com.fortune.app.data.config.api.user.UserAPIRest
import com.fortune.app.data.mapper.user.UProfileMapper
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.repository.api.user.UserAPIRepository
import com.fortune.app.domain.state.UProfileState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UserAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val tokenManager: TokenManager
): UserAPIRepository {
    private val userAPIService = retrofit.create(UserAPIRest::class.java)
}