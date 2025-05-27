package com.fortune.app.domain.repository.api.user

import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.ProfileImageState
import com.fortune.app.domain.state.ProfileToUpdateState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.domain.state.UserPhoneState
import com.fortune.app.network.request.profile.UpdateProfileRequest

interface UProfileAPIRepository {
    suspend fun getProfile(token: String): UProfileState
    suspend fun getProfileToUpdate(s: String): ProfileToUpdateState
    suspend fun updateProfile(s: String, updateProfileRequest: UpdateProfileRequest): DefaultState
    suspend fun getPhone(s: String, userid: Long): UserPhoneState
    suspend fun getProfileByUserID(s: String, userID: Long): UProfileState?
}