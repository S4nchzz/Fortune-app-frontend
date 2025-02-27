package com.fortune.app.ui.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.data.repositories.db.bank_data.AccountDBRepository
import com.fortune.app.data.repositories.db.bank_data.CardDBRepository
import com.fortune.app.data.repositories.db.user.UProfileDBRepository
import com.fortune.app.data.repositories.remote.user.UserAPIRepository
import com.fortune.app.data.repositories.db.user.UserDBRepository
import com.fortune.app.data.repositories.remote.bank_data.AccountAPIRepository
import com.fortune.app.data.repositories.remote.bank_data.CardAPIRepository
import com.fortune.app.data.repositories.remote.user.UProfileAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class User_ViewModel @Inject constructor(
    private val userAPIRepository: UserAPIRepository,
    private val userDBRepository: UserDBRepository,
    private val uProfileAPIRepository: UProfileAPIRepository,
    private val uProfileDBRepository: UProfileDBRepository,
    private val accountAPIRepository: AccountAPIRepository,
    private val accountDBRepository: AccountDBRepository,
    private val cardAPIRepository: CardAPIRepository,
    private val cardDBRepository: CardDBRepository
) : ViewModel() {

    private val _register = MutableLiveData<UserEntity>()
    val register: LiveData<UserEntity> = _register

    fun register(userDTO: UserDTO?) {
        viewModelScope.launch {
            if (userDTO != null) {
                userDBRepository.clearLocalUsers()
                accountDBRepository.clearLocalAccountData()
                cardDBRepository.clearLocalCardData()

                val userEntity: UserEntity = userAPIRepository.register(userDTO.identityDocument, userDTO.email, userDTO.password)
                userDBRepository.saveUser(userEntity)
                _register.value = userEntity
            }
        }
    }

    private val _login = MutableLiveData<UserEntity?>()
    val login: LiveData<UserEntity?> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            userDBRepository.clearLocalUsers()
            accountDBRepository.clearLocalAccountData()
            cardDBRepository.clearLocalCardData()

            val userEntity = userAPIRepository.login(identityDocument, password)
            userDBRepository.saveUser(userEntity)

            if (userEntity.isProfileCreated) {
                val uProfileEntity = uProfileAPIRepository.findProfileByUserId(userEntity.id)
                uProfileDBRepository.saveUprofile(uProfileEntity)
            }

            if (userEntity.digitalSign != null) {
                val accountAPIEntity = accountAPIRepository.findAccountByUserId(userEntity.id)
                accountDBRepository.saveAccount(accountAPIEntity)

                val userCardsEntities: List<CardEntity> = cardAPIRepository.findAllCardsByAccId(accountAPIEntity.account_id)
                cardDBRepository.saveCards(userCardsEntities)
            }

             _login.value = userEntity
        }
    }

    fun resetLoginLiveData() {
        _login.value = null
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
}