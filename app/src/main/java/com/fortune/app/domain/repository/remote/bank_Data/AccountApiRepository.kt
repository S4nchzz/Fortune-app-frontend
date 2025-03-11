package com.fortune.app.domain.repository.remote.bank_Data

import com.fortune.app.domain.model.bank_data.AccountModel

interface AccountApiRepository {
    suspend fun createAccount(user_id: Long): AccountModel
    suspend fun findAccountByUserId(user_id: Long): AccountModel
}