package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.AccountMapper
import com.fortune.app.data.config.api.bank_data.AccountAPIRest
import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.repository.api.bank_Data.AccountApiRepository
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.DefaultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class AccountAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
) : AccountApiRepository {
    private val accountAPIService = retrofit.create(AccountAPIRest::class.java)

    override suspend fun createAccount(token: String): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.createAccount(token)

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }

    override suspend fun findAccount(token: String): AccountState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.findAccount(token)

            if (response.code() == 200 && response.body() != null) {
                AccountState.Success(AccountMapper.mapToDomain(response.body()!!))
            } else {
                AccountState.Error
            }
        }
    }
}