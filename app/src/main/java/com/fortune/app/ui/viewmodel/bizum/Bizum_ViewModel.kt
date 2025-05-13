package com.fortune.app.ui.viewmodel.bizum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.repositories.api.bizum.BizumAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.BizumState
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.domain.state.RequestedBizumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Bizum_ViewModel @Inject constructor(
    val bizumAPIRepositoryImpl: BizumAPIRepositoryImpl,
    val tokenManager: TokenManager
) : ViewModel() {
    private val _makeBizum = MutableLiveData<BizumState>()
    val makeBizum: LiveData<BizumState> = _makeBizum

    fun makeBizum(amount: Double, phone: String, description: String) {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.makeBizum("Bearer ${tokenManager.getToken()}", amount, phone, description)
            _makeBizum.value = response
        }
    }

    private val _myBizums = MutableLiveData<MyBizumsState>()
    val myBizums: LiveData<MyBizumsState> = _myBizums

    fun getMyBizums() {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.getBizums("Bearer ${tokenManager.getToken()}")
            _myBizums.value = response
        }
    }

    private val _requestBizum = MutableLiveData<BizumState>()
    val requestBizum: LiveData<BizumState> = _requestBizum

    fun requestBizum(amount: Double, phone: String, description: String) {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.requestBizum("Bearer ${tokenManager.getToken()}", amount, phone, description)
            _requestBizum.value = response
        }
    }

    private val _myRequestBizums = MutableLiveData<RequestedBizumState>()
    val myRequestBizums: LiveData<RequestedBizumState> = _myRequestBizums

    fun getMyRequestBizums() {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.getRequestBizums("Bearer ${tokenManager.getToken()}")
            _myRequestBizums.value = response
        }
    }

    private val _denyBizum = MutableLiveData<DefaultState>()
    val denyBizum: LiveData<DefaultState> = _denyBizum

    fun denyBizum(bizumID: Int) {
        viewModelScope.launch {
            val response = bizumAPIRepositoryImpl.denyBizumRequest("Bearer ${tokenManager.getToken()}", bizumID)
            _denyBizum.value = response
        }
    }
}