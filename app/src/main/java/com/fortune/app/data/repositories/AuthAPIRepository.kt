package com.fortune.app.data.repositories

import com.fortune.app.data.db.entities.UserEntity
import com.fortune.app.data.remote.UserAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.await
import javax.inject.Inject

class AuthAPIRepository @Inject constructor(
    private val retrofit: Retrofit
) {
    private val userAPIService = retrofit.create(UserAPIRest::class.java)

    suspend fun register(identity_document: String, email: String, password: String): UserEntity {
        return withContext(Dispatchers.IO) {
            userAPIService.register(identity_document, email, password)
        }
    }

    suspend fun login(identityDocument: String, password: String): UserEntity {
        return withContext(Dispatchers.IO) {
            userAPIService.login(identityDocument, password)
        }
    }
}