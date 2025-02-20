package com.fortune.app.data.repositories.remote.user

import com.fortune.app.data.db.entities.UProfileEntity
import com.fortune.app.data.remote.UProfileAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UProfileAPIRepository @Inject constructor(
    private val retrofit: Retrofit
) {
    private val uProfileApiService = retrofit.create(UProfileAPIRest::class.java)

    suspend fun createProfile(uProfileEntity: UProfileEntity): UProfileEntity {
        return withContext(Dispatchers.IO) {
            uProfileApiService.userProfile(uProfileEntity.user_id, uProfileEntity.name, uProfileEntity.address, uProfileEntity.phone, uProfileEntity.online)
        }
    }

    suspend fun findProfileByUserId(id: Long): UProfileEntity {
        return withContext(Dispatchers.IO) {
            uProfileApiService.findProfileByUserId(id)
        }
    }
}