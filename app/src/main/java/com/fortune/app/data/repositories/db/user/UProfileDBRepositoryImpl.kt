package com.fortune.app.data.repositories.db.user

import com.fortune.app.data.config.db.AppDatabase
import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.data.entities.user.UProfileMapper
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.repository.db.user.UProfileDBRepository
import com.fortune.app.domain.repository.remote.user.UProfileApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UProfileDBRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : UProfileDBRepository {
    override suspend fun saveUprofile(uProfileModel: UProfileModel) {
        withContext(Dispatchers.IO) {
            appDatabase.uProfileDao().saveUProfile(UProfileMapper.mapToEntity(uProfileModel))
        }
    }

    override suspend fun findProfile(): UProfileModel {
        return withContext(Dispatchers.IO) {
            UProfileMapper.mapToDomain(appDatabase.uProfileDao().findProfile())
        }
    }

    override suspend fun clearLocalProfiles() {
        return withContext(Dispatchers.IO) {
            appDatabase.uProfileDao().clearLocalProfiles()
        }
    }
}