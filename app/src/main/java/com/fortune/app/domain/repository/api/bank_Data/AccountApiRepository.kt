package com.fortune.app.domain.repository.api.bank_Data

import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.DefaultState

interface AccountApiRepository {
    suspend fun createAccount(token: String): DefaultState
    suspend fun findAccount(token: String): AccountState
}