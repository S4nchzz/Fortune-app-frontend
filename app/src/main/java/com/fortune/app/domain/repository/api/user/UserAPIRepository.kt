package com.fortune.app.domain.repository.api.user

import com.fortune.app.domain.state.UserDataState

interface UserAPIRepository {
    suspend fun getUserData(): UserDataState
}