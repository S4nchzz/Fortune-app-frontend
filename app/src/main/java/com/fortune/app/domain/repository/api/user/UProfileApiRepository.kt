package com.fortune.app.domain.repository.api.user

import com.fortune.app.domain.model.user.UProfileModel

interface UProfileApiRepository {
    suspend fun createProfile(token: String, name: String, address: String, phone: String, online: Boolean): UProfileModel
    suspend fun findProfileByUserId(id: Long): UProfileModel
}