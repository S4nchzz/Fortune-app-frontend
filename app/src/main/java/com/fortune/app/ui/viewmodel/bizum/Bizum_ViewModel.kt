package com.fortune.app.ui.viewmodel.bizum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bizum.BizumAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.MakeBizumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Bizum_ViewModel @Inject constructor(
    val bizumAPIRepositoryImpl: BizumAPIRepositoryImpl,
    val tokenManager: TokenManager
) : ViewModel() {
    private val _makeBizum = MutableLiveData<MakeBizumState>()
    val makeBizum: LiveData<MakeBizumState> = _makeBizum

    fun makeBizum(amount: Double, phone: String, description: String) {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.makeBizum("Bearer ${tokenManager.getToken()}", amount, phone, description)
            _makeBizum.value = response
        }
    }
}