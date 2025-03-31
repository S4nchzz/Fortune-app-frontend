package com.fortune.app.data.repositories.api.user

import com.fortune.app.data.mapper.user.UProfileMapper
import com.fortune.app.data.config.api.user.UProfileAPIRest
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.repository.api.user.UProfileApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UProfileAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : UProfileApiRepository {
    private val uProfileApiService = retrofit.create(UProfileAPIRest::class.java)

    override suspend fun createProfile(token: String, name: String, address: String, phone: String, online: Boolean): UProfileModel {
        return withContext(Dispatchers.IO) {
            UProfileMapper.mapToDomain(uProfileApiService.userProfile(token, name, address, phone,online))
        }
    }

    override suspend fun findProfileByUserId(id: Long): UProfileModel {
        return withContext(Dispatchers.IO) {
            UProfileMapper.mapToDomain(uProfileApiService.findProfileByUserId(id))
        }
    }
}