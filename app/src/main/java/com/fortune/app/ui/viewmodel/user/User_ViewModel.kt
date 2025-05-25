package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UProfileAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.ProfileToUpdateState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.network.request.profile.UpdateProfileRequest
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

    private val _transferCards = MutableLiveData<CardState>()
    val transferCards: LiveData<CardState> = _transferCards

    fun transferCards() {
        viewModelScope.launch {
            val cardState = cardAPIRepositoryImpl.findCards("Bearer ${tokenManager.getToken()}")
            _transferCards.value = cardState
        }
    }

    private val _profileToUpdate = MutableLiveData<ProfileToUpdateState>()
    val profileToUpdate: LiveData<ProfileToUpdateState> = _profileToUpdate

    fun getProfileToUpdate() {
        viewModelScope.launch {
            val profileToUpdateState = uProfileAPIRepository.getProfileToUpdate("Bearer ${tokenManager.getToken()}")
            _profileToUpdate.value = profileToUpdateState
        }
    }

    private val _profileUpdated = MutableLiveData<DefaultState>()
    val profileUpdated: LiveData<DefaultState> = _profileUpdated

    fun updateProfile(name: String, address: String, identityDocument: String, email: String, phone: String) {
        viewModelScope.launch {
            val updated = uProfileAPIRepository.updateProfile("Bearer ${tokenManager.getToken()}", UpdateProfileRequest(name, address, identityDocument, email, phone))
            _profileUpdated.value = updated
        }
    }
}