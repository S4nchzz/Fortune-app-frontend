package com.fortune.app.ui.viewmodel.bank_data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.db.bank_data.AccountDBRepositoryImpl
import com.fortune.app.data.repositories.db.bank_data.CardDBRepositoryImpl
import com.fortune.app.data.repositories.db.user.UserDBRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.domain.model.bank_data.AccountModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Account_ViewModel @Inject constructor(
    val userDBRepositoryImpl: UserDBRepositoryImpl,
    val accountAPIRepositoryImpl: AccountAPIRepositoryImpl,
    val accountDBRepositoryImpl: AccountDBRepositoryImpl,
    val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    val cardDBRepositoryImpl: CardDBRepositoryImpl,
) : ViewModel() {
    private val _account = MutableLiveData<AccountModel>()
    val account: LiveData<AccountModel> = _account

    fun createAccount() {
        viewModelScope.launch {
            val userDBEntity = userDBRepositoryImpl.findUserData()

            val accountModel = accountAPIRepositoryImpl.createAccount(userDBEntity.id)
            accountDBRepositoryImpl.saveAccount(accountModel)

            val cardAPIEntity = cardAPIRepositoryImpl.findMainCardByAccountID(accountModel.accountId)
            cardDBRepositoryImpl.saveCard(cardAPIEntity)

            _account.value = accountModel
        }
    }

    fun clearLocalAccountData() {
        viewModelScope.launch {
            try {
                accountDBRepositoryImpl.clearLocalAccountData()
            } catch (e: Exception) {
                Log.i("Data clear DB", "No data from account to delete")
            }
        }
    }
}