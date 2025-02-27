package com.fortune.app.data.repositories.remote.user

import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.remote.user.UserAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UserAPIRepository @Inject constructor(
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

    suspend fun createDigitalSign(user_id: Long, ds: Int): UserEntity {
        return withContext(Dispatchers.IO) {
            userAPIService.createDigitalSign(user_id, ds)
        }
    }

    suspend fun updateProfileCreationStatus(userProfileStatusUpdated: UserEntity): UserEntity {
        return withContext(Dispatchers.IO) {
            userAPIService.updateProfileStatus(userProfileStatusUpdated.id, userProfileStatusUpdated.isProfileCreated)
        }
    }
}