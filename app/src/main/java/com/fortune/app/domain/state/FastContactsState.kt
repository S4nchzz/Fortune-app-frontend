package com.fortune.app.domain.state

import com.fortune.app.network.response.account.FastContactResponse

sealed class FastContactsState {
    data class Success(
        val contactResponseList: List<FastContactResponse>
    ): FastContactsState()

    object Error: FastContactsState()
}
