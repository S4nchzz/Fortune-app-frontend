package com.fortune.app.ui.viewmodel.bank_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.repositories.db.user.UserDBRepository
import com.fortune.app.data.repositories.remote.bank_data.CardAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Card_ViewModel @Inject constructor(
    val cardAPIRepository: CardAPIRepository,
    val userDBRepository: UserDBRepository
) : ViewModel() {
    private val _card = MutableLiveData<CardEntity>()
    val card: LiveData<CardEntity> = _card
}