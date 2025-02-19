package com.fortune.app.ui.viewmodal.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.db.entities.UserEntity
import com.fortune.app.data.repositories.AuthAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Auth_ViewModel @Inject constructor(
   private val userAPIRepository: AuthAPIRepository
) : ViewModel() {

    private val _register = MutableLiveData<UserEntity>()
    val register: LiveData<UserEntity> = _register

    fun register(identity_document: String, email: String, password: String) {
        viewModelScope.launch {
            val userEntity: UserEntity = userAPIRepository.register(identity_document, email, password)
            _register.value = userEntity
        }
    }

    private val _login = MutableLiveData<UserEntity>()
    val login: LiveData<UserEntity> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            val userEntity = userAPIRepository.login(identityDocument, password)

            _login.value = userEntity
        }
    }
}