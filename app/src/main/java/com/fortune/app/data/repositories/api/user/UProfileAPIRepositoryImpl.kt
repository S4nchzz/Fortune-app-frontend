package com.fortune.app.data.repositories.api.user

import com.fortune.app.data.config.api.user.UProfileAPIRest
import com.fortune.app.data.mapper.user.UProfileMapper
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.repository.api.user.UProfileAPIRepository
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.ProfileToUpdateState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.network.request.profile.UpdateProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class UProfileAPIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : UProfileAPIRepository {
    private val uProfileAPIService = retrofit.create(UProfileAPIRest::class.java)

    override suspend fun getProfile(token: String): UProfileState {
        return withContext(Dispatchers.IO) {
            val response = uProfileAPIService.getProfile(token)
            if (response.code() == 200 && response.body() != null) {
                UProfileState.Success(UProfileMapper.mapToDomain(response.body()!!))
            } else {
                UProfileState.Error
            }
        }
    }

    override suspend fun getProfileToUpdate(token: String): ProfileToUpdateState {
        return withContext(Dispatchers.IO) {
            val response = uProfileAPIService.getProfileToUpdate(token)

            if (response.code() == 200 && response.body() != null) {
                ProfileToUpdateState.Success(response.body()!!)
            } else {
                ProfileToUpdateState.Error
            }
        }
    }

    override suspend fun updateProfile(token: String, updateProfileRequest: UpdateProfileRequest): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = uProfileAPIService.updateProfile(updateProfileRequest)

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }
}