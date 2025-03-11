package com.fortune.app.ui.viewmodel.bank_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fortune.app.data.repositories.db.user.UserDBRepositoryImpl
import com.fortune.app.data.repositories.remote.bank_data.CardAPIRepositoryImpl
import com.fortune.app.domain.model.bank_data.CardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Card_ViewModel @Inject constructor(
    val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    val userDBRepositoryImpl: UserDBRepositoryImpl
) : ViewModel() {
    private val _card = MutableLiveData<CardModel>()
    val card: LiveData<CardModel> = _card
}