package com.fortune.app.ui.viewmodel.bank_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.CardBalanceState
import com.fortune.app.domain.state.CardCvvState
import com.fortune.app.domain.state.CardExpDateState
import com.fortune.app.domain.state.CardNumberState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState
import com.fortune.app.domain.state.MovementState
import com.fortune.app.domain.state.NewBalanceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Card_ViewModel @Inject constructor(
    val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    val tokenManager: TokenManager
) : ViewModel() {
    private val _movementState = MutableLiveData<MovementState?>()
    val movementState: LiveData<MovementState?> = _movementState

    fun findCardMovements(card_uuid: String) {
        viewModelScope.launch {
            val cardMovementState = cardAPIRepositoryImpl.findMovements("Bearer ${tokenManager.getToken()}", card_uuid)
            _movementState.value = cardMovementState
        }
    }

    fun reloadRViewObserver(card_uuid: String) {
        viewModelScope.launch {
            findCardMovements(card_uuid)
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

    private val _cardNumber = MutableLiveData<CardNumberState>()
    val cardNumber: LiveData<CardNumberState> = _cardNumber

    fun getCardNumber(card_uuid: String) {
        viewModelScope.launch {
            val cardNumberState = cardAPIRepositoryImpl.getCardNumber("Bearer ${tokenManager.getToken()}", card_uuid)
            _cardNumber.value = cardNumberState
        }
    }

    private val _cardExpDate = MutableLiveData<CardExpDateState>()
    val cardExpDate: LiveData<CardExpDateState> = _cardExpDate

    fun getExpDate(card_uuid: String) {
        viewModelScope.launch {
            val cardExpDateState = cardAPIRepositoryImpl.getExpDate("Bearer ${tokenManager.getToken()}", card_uuid)
            _cardExpDate.value = cardExpDateState
        }
    }

    private val _cardCvvState = MutableLiveData<CardCvvState>()
    val cardCvvState: LiveData<CardCvvState> = _cardCvvState

    fun getCvv(card_uuid: String) {
        viewModelScope.launch {
            val cardCvvState = cardAPIRepositoryImpl.getCvv("Bearer ${tokenManager.getToken()}", card_uuid)
            _cardCvvState.value = cardCvvState
        }
    }

    private val _cardBalanceState = MutableLiveData<CardBalanceState>()
    val cardBalanceState: LiveData<CardBalanceState> = _cardBalanceState

    fun getBalance(card_uuid: String) {
        viewModelScope.launch {
            val responseState = cardAPIRepositoryImpl.getCardBalance("Bearer ${tokenManager.getToken()}", card_uuid)
            _cardBalanceState.value = responseState
        }
    }

    private val _addBalanceState = MutableLiveData<NewBalanceState>()
    val addBalanceState: LiveData<NewBalanceState> = _addBalanceState

    fun addBalance(newBalance: Double) {
        viewModelScope.launch {
            val responseState = cardAPIRepositoryImpl.addNewBalance(newBalance, "Bearer ${tokenManager.getToken()}")
            _addBalanceState.value = responseState
        }
    }

    private val _addNewCard = MutableLiveData<DefaultState>()
    val addNewCard: LiveData<DefaultState> = _addNewCard

    fun addCard() {
        viewModelScope.launch {
            val responseState = cardAPIRepositoryImpl.addNewCard("Bearer ${tokenManager.getToken()}")
            _addNewCard.value = responseState
        }
    }
}