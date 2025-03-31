package com.fortune.app.ui.viewmodel.user

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.data.repositories.api.user.UserAPIRepositoryImpl
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Auth_ViewModel @Inject constructor(
    private val userAPIRepositoryImpl: UserAPIRepositoryImpl,
    private val application: Application
) : ViewModel() {

    private val _register = MutableLiveData<DefaultState>()
    val register: LiveData<DefaultState> = _register

    fun register(userDTO: UserDTO?, uProfileDTO: UProfileDTO) {
        viewModelScope.launch {
            if (userDTO != null) {
                val registerState = userAPIRepositoryImpl.register(userDTO, uProfileDTO)
                _register.value = registerState
            }
        }
    }

    private val _login = MutableLiveData<LoginState?>()
    val login: LiveData<LoginState?> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            val loginState: LoginState = userAPIRepositoryImpl.login(identityDocument, password)

            if(loginState is LoginState.Success) {
                val sharedPrefs = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                sharedPrefs.edit().putString("auth_token", loginState.responseInfo.token).apply()
                Log.e("mitoken", loginState.responseInfo.token)
            }

             _login.value = loginState
        }
    }

    private val _digitalSign = MutableLiveData<DefaultState>()
    val digitalSign: LiveData<DefaultState> = _digitalSign

    fun createDigitalSign(ds: Int) {
        viewModelScope.launch {
            val sharedPreferences = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("auth_token", null)

            val pinCreationState = userAPIRepositoryImpl.createDigitalSign("Bearer ${token.toString()}", ds)

            _digitalSign.value = pinCreationState
        }
    }

    fun resetLoginObserve() {
        this._login.value = null
    }
}