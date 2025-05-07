package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.AccountMapper
import com.fortune.app.data.config.api.bank_data.AccountAPIRest
import com.fortune.app.domain.repository.api.bank_Data.AccountApiRepository
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.PaymentSimulationState
import com.fortune.app.network.request.movement.SimulatePaymentRequest
import com.fortune.app.network.response.account.PaymentSimulationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
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

    override suspend fun simulatePayment(token: String, amount: Double, receptorEntity: String, cardUUID: String): PaymentSimulationState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.simulatePayment(token, SimulatePaymentRequest(amount, receptorEntity, cardUUID))

            if (response.code() == 200 && response.body() != null) {
                PaymentSimulationState.Success(response.body()!!.paymentSimulated)
            } else {
                PaymentSimulationState.Error
            }
        }
    }
}