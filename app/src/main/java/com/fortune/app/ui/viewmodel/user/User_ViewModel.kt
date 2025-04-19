package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UProfileAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UserAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.repository.api.user.UProfileAPIRepository
import com.fortune.app.domain.repository.api.user.UserAPIRepository
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.UProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class User_ViewModel @Inject constructor(
    val uProfileAPIRepository: UProfileAPIRepositoryImpl,
    val accountAPIRepository: AccountAPIRepositoryImpl,
    val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    val tokenManager: TokenManager
) : ViewModel() {
    private val _profile = MutableLiveData<UProfileState>()
    val profile: LiveData<UProfileState> = _profile

    fun getProfile() {
        viewModelScope.launch {
            val userDataState = uProfileAPIRepository.getProfile("Bearer ${tokenManager.getToken()}")
            _profile.value = userDataState
        }
    }

    private val _account = MutableLiveData<AccountState>()
    val account: LiveData<AccountState> = _account

    fun getAccount() {
        viewModelScope.launch {
            val apiAccount = accountAPIRepository.findAccount("Bearer ${tokenManager.getToken()}")
            _account.value = apiAccount
        }
    }

    private val _cards = MutableLiveData<CardState>()
    val cards: LiveData<CardState> = _cards

    fun getCards() {
        viewModelScope.launch {
            val cardState = cardAPIRepositoryImpl.findCards("Bearer ${tokenManager.getToken()}")
            _cards.value = cardState
        }
    }
}