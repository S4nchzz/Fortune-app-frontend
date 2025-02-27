package com.fortune.app.data.repositories.remote.bank_data

import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.data.remote.bank_data.AccountAPIRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class AccountAPIRepository @Inject constructor(
    val retrofit: Retrofit
) {
    private val accountAPIService = retrofit.create(AccountAPIRest::class.java)

    suspend fun createAccount(user_id: Long): AccountEntity {
        return withContext(Dispatchers.IO) {
            accountAPIService.createAccount(user_id)
        }
    }

    suspend fun findAccountByUserId(user_id: Long): AccountEntity {
        return withContext(Dispatchers.IO) {
            accountAPIService.findAccountByUserId(user_id)
        }
    }
}