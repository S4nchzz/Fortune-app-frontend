package com.fortune.app.domain.state

import com.google.gson.annotations.SerializedName

sealed class ProfileImageState() {
    data class Success(val pfpBasee64: String): ProfileImageState()
    object Error: ProfileImageState()
}