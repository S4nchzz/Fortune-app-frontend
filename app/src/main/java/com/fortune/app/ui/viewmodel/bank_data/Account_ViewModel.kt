package com.fortune.app.ui.viewmodel.bank_data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.state.AccountCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Account_ViewModel @Inject constructor(
    private val accountAPIRepositoryImpl: AccountAPIRepositoryImpl,
    private val application: Application
) : ViewModel() {
    private val _account = MutableLiveData<AccountCreationState>()
    val account: LiveData<AccountCreationState> = _account

    fun createAccount() {
        viewModelScope.launch {
            val sharedPreferences = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("auth_token", null)

            val accountModel = accountAPIRepositoryImpl.createAccount("Bearer ${token.toString()}")

            _account.value = accountModel
        }
    }
}