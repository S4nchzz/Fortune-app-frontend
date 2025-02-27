package com.fortune.app.data.repositories.remote.user

import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.data.remote.user.UProfileAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UProfileAPIRepository @Inject constructor(
    private val retrofit: Retrofit
) {
    private val uProfileApiService = retrofit.create(UProfileAPIRest::class.java)

    suspend fun createProfile(userId: Long, name: String, address: String, phone: String, online: Boolean): UProfileEntity {
        return withContext(Dispatchers.IO) {
            uProfileApiService.userProfile(userId, name, address, phone,online)
        }
    }

    suspend fun findProfileByUserId(id: Long): UProfileEntity {
        return withContext(Dispatchers.IO) {
            uProfileApiService.findProfileByUserId(id)
        }
    }
}