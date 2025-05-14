package com.fortune.app.domain.repository.api.bank_Data

import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.AccountDataState
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.PaymentSimulationState
import com.fortune.app.network.response.account.AccountBalanceResponse
import com.fortune.app.network.response.account.PaymentSimulationResponse
import retrofit2.Response

interface AccountApiRepository {
    suspend fun createAccount(token: String): DefaultState
    suspend fun findAccount(token: String): AccountState
    suspend fun simulatePayment(token: String, amount: Double, receptorEntity: String, cardUUID: String): PaymentSimulationState
    suspend fun getAccountBalance(token: String): AccountBalanceState
    suspend fun getAccountData(s: String): AccountDataState
}