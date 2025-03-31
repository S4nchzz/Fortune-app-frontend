package com.fortune.app.domain.repository.remote.bank_Data

import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.state.AccountCreationState

interface AccountApiRepository {
    suspend fun createAccount(token: String): AccountCreationState
    suspend fun findAccountByUserId(token: String): AccountModel
}