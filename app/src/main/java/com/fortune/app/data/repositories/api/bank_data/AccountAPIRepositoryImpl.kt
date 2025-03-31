package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.AccountMapper
import com.fortune.app.data.config.api.bank_data.AccountAPIRest
import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.repository.remote.bank_Data.AccountApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class AccountAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
) : AccountApiRepository {
    private val accountAPIService = retrofit.create(AccountAPIRest::class.java)

    override suspend fun createAccount(user_id: Long): AccountModel {
        return withContext(Dispatchers.IO) {
            AccountMapper.mapToDomain(accountAPIService.createAccount(user_id))
        }
    }

    override suspend fun findAccountByUserId(user_id: Long): AccountModel {
        return withContext(Dispatchers.IO) {
            AccountMapper.mapToDomain(accountAPIService.findAccountByUserId(user_id))
        }
    }
}