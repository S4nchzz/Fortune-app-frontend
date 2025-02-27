package com.fortune.app.ui.viewmodel.bank_data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.data.repositories.db.bank_data.AccountDBRepository
import com.fortune.app.data.repositories.db.bank_data.CardDBRepository
import com.fortune.app.data.repositories.db.user.UserDBRepository
import com.fortune.app.data.repositories.remote.bank_data.AccountAPIRepository
import com.fortune.app.data.repositories.remote.bank_data.CardAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Account_ViewModel @Inject constructor(
    val userDBRepository: UserDBRepository,
    val accountAPIRepository: AccountAPIRepository,
    val accountDBRepository: AccountDBRepository,
    val cardAPIRepository: CardAPIRepository,
    val cardDBRepository: CardDBRepository,
) : ViewModel() {
    private val _account = MutableLiveData<AccountEntity>()
    val account: LiveData<AccountEntity> = _account

    fun createAccount() {
        viewModelScope.launch {
            val userDBEntity = userDBRepository.findUserData()

            val accountEntity = accountAPIRepository.createAccount(userDBEntity.id) // Save and get from API
            accountDBRepository.saveAccount(accountEntity) // Save in database

            val cardAPIEntity = cardAPIRepository.findMainCardByAccountID(accountEntity.account_id)
            cardDBRepository.saveCard(cardAPIEntity)

            _account.value = accountEntity
        }
    }

    fun clearLocalAccountData() {
        viewModelScope.launch {
            try {
                accountDBRepository.clearLocalAccountData()
            } catch (e: Exception) {
                Log.i("Data clear DB", "No data from account to delete")
            }
        }
    }
}