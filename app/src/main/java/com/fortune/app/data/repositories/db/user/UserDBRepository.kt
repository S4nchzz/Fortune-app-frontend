package com.fortune.app.data.repositories.db.user

import android.util.Log
import com.fortune.app.data.db.AppDatabase
import com.fortune.app.data.db.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDBRepository @Inject constructor(
    private val appDatabase: AppDatabase
){
    suspend fun saveUser(userEntity: UserEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().saveUser(userEntity)
        }
    }

    suspend fun updateDigitalSign(userEntity: UserEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().updateDigitalSign(userEntity)
        }
    }

    suspend fun findUserData(): UserEntity {
        return withContext(Dispatchers.IO) {
            appDatabase.userDao().findUserData()
        }
    }

    suspend fun updateProfileStatus(userEntityUpdated: UserEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().updateProfileStatus(userEntityUpdated)
        }
    }

    suspend fun clearLocalUsers() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.userDao().clearLocalUsers()
            } catch (e: Exception) {
                Log.i("clearLocalUsersData", "No adta found to clear")
            }
        }
    }
}