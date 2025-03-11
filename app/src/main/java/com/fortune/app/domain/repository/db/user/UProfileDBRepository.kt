package com.fortune.app.domain.repository.db.user

import com.fortune.app.domain.model.user.UProfileModel

interface UProfileDBRepository {
    suspend fun saveUprofile(entityResponseFromAPI: UProfileModel)
    suspend fun findProfile(): UProfileModel?
    suspend fun clearLocalProfiles()
}