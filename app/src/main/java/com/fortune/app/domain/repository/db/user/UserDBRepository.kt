package com.fortune.app.domain.repository.db.user

import com.fortune.app.domain.model.user.UserModel


interface UserDBRepository {
    suspend fun saveUser(userModel: UserModel)
    suspend fun updateDigitalSign(userModel: UserModel)
    suspend fun findUserData(): UserModel
    suspend fun updateProfileStatus(userEntityUpdated: UserModel)
    suspend fun clearLocalUsers()
}