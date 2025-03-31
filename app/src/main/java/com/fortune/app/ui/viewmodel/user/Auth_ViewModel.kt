package com.fortune.app.ui.viewmodel.user

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.data.repositories.api.user.UserAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UProfileAPIRepositoryImpl
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.inject.Inject

@HiltViewModel
class Auth_ViewModel @Inject constructor(
    private val userAPIRepositoryImpl: UserAPIRepositoryImpl,
    private val uProfileAPIRepositoryImpl: UProfileAPIRepositoryImpl,
    private val accountAPIRepositoryImpl: AccountAPIRepositoryImpl,
    private val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    private val application: Application
) : ViewModel() {

    private val _register = MutableLiveData<UserModel>()
    val register: LiveData<UserModel> = _register

    fun register(userDTO: UserDTO?) {
        viewModelScope.launch {
            if (userDTO != null) {
                val userModel: UserModel = userAPIRepositoryImpl.register(userDTO.identityDocument, userDTO.email, userDTO.password)
                _register.value = userModel
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
                sharedPrefs.edit().putString("auth_token", loginState.token).apply()
                Log.e("mitoken", loginState.token)
            }

             _login.value = loginState
        }
    }

    private val _digitalSign = MutableLiveData<UserModel>()
    val digitalSign: LiveData<UserModel> = _digitalSign

    fun createDigitalSign(ds: Int) {
        viewModelScope.launch {
            val sharedPreferences = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("auth_token", null)

            val userModel: UserModel = userAPIRepositoryImpl.createDigitalSign("Bearer ${token.toString()}", ds)

            _digitalSign.value = userModel
        }
    }

    fun resetLoginObserve() {
        this._login.value = null
    }
}