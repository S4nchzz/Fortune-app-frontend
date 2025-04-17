package com.fortune.app.domain.state

import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.domain.model.user.UProfileModel

sealed class UProfileState {
    data class Success(val uProfileModel: UProfileModel): UProfileState()
    object Error: UProfileState()
}