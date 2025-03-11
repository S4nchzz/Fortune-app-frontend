package com.fortune.app.domain.repository.remote.user

import com.fortune.app.domain.model.user.UserModel

interface UserApiRepository {
    suspend fun register(identity_document: String, email: String, password: String): UserModel
    suspend fun login(identityDocument: String, password: String): UserModel?
    suspend fun createDigitalSign(user_id: Long, ds: Int): UserModel
    suspend fun updateProfileCreationStatus(userProfileStatusUpdated: UserModel): UserModel
}