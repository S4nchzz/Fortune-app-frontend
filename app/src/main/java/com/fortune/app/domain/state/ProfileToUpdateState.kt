package com.fortune.app.domain.state

import com.fortune.app.network.response.profile.ProfileToUpdateResponse

sealed class ProfileToUpdateState{
    data class Success(val profileToUpdateResponse: ProfileToUpdateResponse): ProfileToUpdateState()
    object Error: ProfileToUpdateState()
}
