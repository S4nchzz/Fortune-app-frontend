package com.fortune.app.ui.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.db.entities.UProfileEntity
import com.fortune.app.data.db.entities.UserEntity
import com.fortune.app.data.repositories.remote.user.UserAPIRepository
import com.fortune.app.data.repositories.db.user.UserDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class User_ViewModel @Inject constructor(
    private val userAPIRepository: UserAPIRepository,
    private val userDBRepository: UserDBRepository
) : ViewModel() {

    private val _register = MutableLiveData<UserEntity>()
    val register: LiveData<UserEntity> = _register

    fun register(identity_document: String, email: String, password: String) {
        viewModelScope.launch {
            val userEntity: UserEntity = userAPIRepository.register(identity_document, email, password)
            userDBRepository.saveUser(userEntity)
            _register.value = userEntity
        }
    }

    private val _login = MutableLiveData<UserEntity>()
    val login: LiveData<UserEntity> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            val userEntity = userAPIRepository.login(identityDocument, password)
            userDBRepository.saveUser(userEntity)

            _login.value = userEntity
        }
    }

    private val _digitalSign = MutableLiveData<UserEntity>()
    val digitalSign: LiveData<UserEntity> = _digitalSign

    fun createDigitalSign(ds: Int) {
        viewModelScope.launch {
            val userFromDB: UserEntity = userDBRepository.findUserData()
            val userEntity: UserEntity = userAPIRepository.createDigitalSign(userFromDB.id, ds)

            userDBRepository.updateDigitalSign(userEntity)

            _digitalSign.value = userEntity
        }
    }

    fun clearLocalUsers() {
        viewModelScope.launch {
            userDBRepository.clearLocalUsers()
        }
    }
}