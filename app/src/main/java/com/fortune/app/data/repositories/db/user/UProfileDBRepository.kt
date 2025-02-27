package com.fortune.app.data.repositories.db.user

import com.fortune.app.data.db.AppDatabase
import com.fortune.app.data.entities.user.UProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UProfileDBRepository @Inject constructor(
    private val appDatabase: AppDatabase
){
    suspend fun saveUprofile(entityResponseFromAPI: UProfileEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.uProfileDao().saveUProfile(entityResponseFromAPI)
        }
    }

    suspend fun findProfile(): UProfileEntity? {
        return withContext(Dispatchers.IO) {
            appDatabase.uProfileDao().findProfile()
        }
    }

    suspend fun clearLocalProfiles() {
        return withContext(Dispatchers.IO) {
            appDatabase.uProfileDao().clearLocalProfiles()
        }
    }
}