package com.fortune.app.ui.viewmodel.bank_data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.AccountDataState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.PaymentSimulationState
import com.fortune.app.network.response.account.AccountDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Account_ViewModel @Inject constructor(
    private val accountAPIRepositoryImpl: AccountAPIRepositoryImpl,
    private val application: Application,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _account = MutableLiveData<DefaultState>()
    val account: LiveData<DefaultState> = _account

    fun createAccount() {
        viewModelScope.launch {
            val accountModel = accountAPIRepositoryImpl.createAccount("Bearer ${tokenManager.getToken()}")

            _account.value = accountModel
        }
    }

    private val _simulatePaymentState = MutableLiveData<PaymentSimulationState>()
    val simulatePaymentState: LiveData<PaymentSimulationState> = _simulatePaymentState

    fun simulatePayment(cardUUID: String, amount: Double, receptorEntity: String) {
        viewModelScope.launch {
            val responseState = accountAPIRepositoryImpl.simulatePayment("Bearer ${tokenManager.getToken()}", amount, receptorEntity, cardUUID)
            _simulatePaymentState.value = responseState
        }
    }

    private val _accountBalanceState = MutableLiveData<AccountBalanceState>()
    val accountBalanceState: LiveData<AccountBalanceState> = _accountBalanceState

    fun getAccountBalance() {
        viewModelScope.launch {
            val responseState = accountAPIRepositoryImpl.getAccountBalance("Bearer ${tokenManager.getToken()}")
            _accountBalanceState.value = responseState
        }
    }

    private val _accountBizumBalanceState = MutableLiveData<AccountBalanceState>()
    val accountBizumBalanceState: LiveData<AccountBalanceState> = _accountBizumBalanceState

    fun getAccountBalanceBizum() {
        viewModelScope.launch {
            val responseState = accountAPIRepositoryImpl.getAccountBalance("Bearer ${tokenManager.getToken()}")
            _accountBizumBalanceState.value = responseState
        }
    }

    private val _accountDataState = MutableLiveData<AccountDataState>()
    val accountDataState: LiveData<AccountDataState> = _accountDataState

    fun getAccountData() {
        viewModelScope.launch {
            val responseState = accountAPIRepositoryImpl.getAccountData("Bearer ${tokenManager.getToken()}")
            _accountDataState.value = responseState
        }
    }
}