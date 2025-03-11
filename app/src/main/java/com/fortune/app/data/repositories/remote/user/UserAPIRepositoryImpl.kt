package com.fortune.app.data.repositories.remote.user

import android.util.Log
import com.fortune.app.data.mapper.user.UserMapper
import com.fortune.app.data.config.remote.user.UserAPIRest
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.repository.remote.user.UserApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UserAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : UserApiRepository {
    private val userAPIService = retrofit.create(UserAPIRest::class.java)

    override suspend fun register(identity_document: String, email: String, password: String): UserModel {
        return withContext(Dispatchers.IO) {
            UserMapper.mapToDomain(userAPIService.register(identity_document, email, password))
        }
    }

    override suspend fun login(identityDocument: String, password: String): UserModel? {
        return withContext(Dispatchers.IO) {
            try {
                UserMapper.mapToDomain(userAPIService.login(identityDocument, password))
            } catch (e: Exception) {
                Log.e("LOGIN", "User credentials are incorrect", e)
                null
            }
        }
    }

    override suspend fun createDigitalSign(user_id: Long, ds: Int): UserModel {
        return withContext(Dispatchers.IO) {
            UserMapper.mapToDomain(userAPIService.createDigitalSign(user_id, ds))
        }
    }
}