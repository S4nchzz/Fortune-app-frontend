package com.fortune.app.ui.viewmodel.auth

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.data.repositories.api.auth.AuthAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import com.fortune.app.domain.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Auth_ViewModel @Inject constructor(
    private val authAPIRepositoryImpl: AuthAPIRepositoryImpl,
    private val application: Application,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _register = MutableLiveData<RegisterState>()
    val register: LiveData<RegisterState> = _register

    fun register(userDTO: UserDTO?, uProfileDTO: UProfileDTO) {
        viewModelScope.launch {
            if (userDTO != null) {
                val registerState = authAPIRepositoryImpl.register(userDTO, uProfileDTO)
                _register.value = registerState
            }
        }
    }

    private val _login = MutableLiveData<LoginState?>()
    val login: LiveData<LoginState?> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            val loginState: LoginState = authAPIRepositoryImpl.login(identityDocument, password)

            if(loginState is LoginState.Success) {
                tokenManager.saveToken(loginState.responseInfo.token)
                Log.e("mitoken", tokenManager.getToken()!!)
            }

             _login.value = loginState
        }
    }

    private val _digitalSign = MutableLiveData<DefaultState>()
    val digitalSign: LiveData<DefaultState> = _digitalSign

    fun createDigitalSign(ds: Int) {
        viewModelScope.launch {
            val pinCreationState = authAPIRepositoryImpl.createDigitalSign("Bearer ${tokenManager.getToken()}", ds)

            _digitalSign.value = pinCreationState
        }
    }

    private val _signOperation = MutableLiveData<DefaultState>()
    val signOperation: LiveData<DefaultState> = _signOperation

    fun signOperation(ds: Int) {
        viewModelScope.launch {
            val signOperationState = authAPIRepositoryImpl.signOperation("Bearer ${tokenManager.getToken()}", ds)
            _signOperation.value = signOperationState
        }
    }

    fun resetLoginObserve() {
        this._login.value = null
    }
}