package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.AccountMapper
import com.fortune.app.data.config.api.bank_data.AccountAPIRest
import com.fortune.app.data.mapper.bank_data.CardMovementMapper
import com.fortune.app.domain.model.bank_data.MovementModel
import com.fortune.app.domain.repository.api.bank_Data.AccountApiRepository
import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.AccountDataState
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.FastContactsState
import com.fortune.app.domain.state.MovementState
import com.fortune.app.domain.state.PaymentSimulationState
import com.fortune.app.network.request.movement.SimulatePaymentRequest
import com.fortune.app.network.response.account.FastContactResponse
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

    override suspend fun getAccountBalance(token: String): AccountBalanceState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.getAccountBalance(token)

            if (response.code() == 200 && response.body() != null) {
                AccountBalanceState.Success(response.body()!!)
            } else {
                AccountBalanceState.Error
            }
        }
    }

    override suspend fun getAccountData(token: String): AccountDataState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.getAccountData(token)

            if (response.code() == 200 && response.body() != null) {
                AccountDataState.Success(response.body()!!.accountID, response.body()!!.accountBalance)
            } else {
                AccountDataState.Error
            }
        }
    }

    override suspend fun getAccountMovements(token: String): MovementState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.getAccountMovements(token)

            if (response.code() == 200 && response.body() != null) {
                val accountMovementModel: MutableList<MovementModel> = mutableListOf()
                response.body()!!.forEach { item ->
                    accountMovementModel.add(CardMovementMapper.mapToDomain(item))
                }
                MovementState.Success(accountMovementModel)
            } else {
                MovementState.Error
            }
        }
    }

    override suspend fun getFastContacts(token: String): FastContactsState {
        return withContext(Dispatchers.IO) {
            val response = accountAPIService.getFastContacts(token)

            if (response.code() == 200 && response.body() != null) {
                var fastContactList: List<FastContactResponse>? = response.body()

                if (fastContactList == null) {
                    fastContactList = listOf()
                }

                FastContactsState.Success(fastContactList)
            } else {
                FastContactsState.Error
            }
        }
    }
}