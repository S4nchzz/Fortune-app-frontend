package com.fortune.app.ui.viewmodel.bank_data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.PaymentSimulationState
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
}