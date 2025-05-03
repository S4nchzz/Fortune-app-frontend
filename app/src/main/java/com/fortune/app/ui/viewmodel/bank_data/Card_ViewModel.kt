package com.fortune.app.ui.viewmodel.bank_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.model.bank_data.CardMovementModel
import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Card_ViewModel @Inject constructor(
    val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    val tokenManager: TokenManager
) : ViewModel() {
    private val _movementState = MutableLiveData<CardMovementState>()
    val movementState: LiveData<CardMovementState> = _movementState

    fun findCardMovements(card_uuid: String) {
        viewModelScope.launch {
            val cardMovementState = cardAPIRepositoryImpl.findMovements("Bearer ${tokenManager.getToken()}", card_uuid)
            _movementState.value = cardMovementState
        }
    }

    private val _lockCardState = MutableLiveData<LockCardState>()
    val lockCardState: LiveData<LockCardState> = _lockCardState

    fun lockCard(card_uuid: String) {
        viewModelScope.launch {
            val cardLockState = cardAPIRepositoryImpl.lockCard("Bearer ${tokenManager.getToken()}", card_uuid)
            _lockCardState.value = cardLockState
        }
    }

    private val _isCardLockedState = MutableLiveData<LockCardState>()
    val isCardLockedState: LiveData<LockCardState> = _isCardLockedState

    fun isCardLocked(card_uuid: String) {
        viewModelScope.launch {
            val cardLockState = cardAPIRepositoryImpl.isCardLocked("Bearer ${tokenManager.getToken()}", card_uuid)
            _isCardLockedState.value = cardLockState
        }
    }
}