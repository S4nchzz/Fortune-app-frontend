package com.fortune.app.domain.repository.api.user

import com.fortune.app.domain.state.UProfileState

interface UProfileAPIRepository {
    suspend fun getProfile(token: String): UProfileState
}