package com.fortune.app.data.repositories.db.user

import android.util.Log
import com.fortune.app.data.config.db.AppDatabase
import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.mapper.user.UserMapper
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.repository.db.user.UserDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDBRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : UserDBRepository {
    override suspend fun saveUser(userModel: UserModel) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().saveUser(UserMapper.mapToEntity(userModel))
        }
    }

    override suspend fun updateDigitalSign(userModel: UserModel) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().updateDigitalSign(UserMapper.mapToEntity(userModel))
        }
    }

    override suspend fun findUserData(): UserModel {
        return withContext(Dispatchers.IO) {
            UserMapper.mapToDomain(appDatabase.userDao().findUserData())
        }
    }

    override suspend fun clearLocalUsers() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.userDao().clearLocalUsers()
            } catch (e: Exception) {
                Log.i("clearLocalUsersData", "No data found to clear")
            }
        }
    }
}