package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.data.repositories.db.bank_data.AccountDBRepositoryImpl
import com.fortune.app.data.repositories.db.bank_data.CardDBRepositoryImpl
import com.fortune.app.data.repositories.db.user.UProfileDBRepositoryImpl
import com.fortune.app.data.repositories.remote.user.UserAPIRepositoryImpl
import com.fortune.app.data.repositories.db.user.UserDBRepositoryImpl
import com.fortune.app.data.repositories.remote.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.remote.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.remote.user.UProfileAPIRepositoryImpl
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.model.user.UserModel
import com.fortune.app.domain.state.State_Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class User_ViewModel @Inject constructor(
    private val userAPIRepositoryImpl: UserAPIRepositoryImpl,
    private val userDBRepositoryImpl: UserDBRepositoryImpl,
    private val uProfileAPIRepositoryImpl: UProfileAPIRepositoryImpl,
    private val uProfileDBRepositoryImpl: UProfileDBRepositoryImpl,
    private val accountAPIRepositoryImpl: AccountAPIRepositoryImpl,
    private val accountDBRepositoryImpl: AccountDBRepositoryImpl,
    private val cardAPIRepositoryImpl: CardAPIRepositoryImpl,
    private val cardDBRepositoryImpl: CardDBRepositoryImpl
) : ViewModel() {

    private val _register = MutableLiveData<UserModel>()
    val register: LiveData<UserModel> = _register

    fun register(userDTO: UserDTO?) {
        viewModelScope.launch {
            if (userDTO != null) {
                clearDBL()

                val userModel: UserModel = userAPIRepositoryImpl.register(userDTO.identityDocument, userDTO.email, userDTO.password)
                userDBRepositoryImpl.saveUser(userModel)
                _register.value = userModel
            }
        }
    }

    private val _login = MutableLiveData<State_Login?>()
    val login: LiveData<State_Login?> = _login

    fun login(identityDocument: String, password: String) {
        viewModelScope.launch {
            clearDBL()

            val userModel = userAPIRepositoryImpl.login(identityDocument, password)

            if (userModel == null) {
                _login.value = State_Login.Error
                return@launch
            }

            userDBRepositoryImpl.saveUser(userModel)

            val uProfileEntity = uProfileAPIRepositoryImpl.findProfileByUserId(userModel.id)
            uProfileDBRepositoryImpl.saveUprofile(uProfileEntity)

            if (userModel.digitalSign != null) {
                val accountAPIEntity = accountAPIRepositoryImpl.findAccountByUserId(userModel.id)
                accountDBRepositoryImpl.saveAccount(accountAPIEntity)

                val userCardsEntities: List<CardModel> = cardAPIRepositoryImpl.findAllCardsByAccId(accountAPIEntity.accountId)
                cardDBRepositoryImpl.saveCards(userCardsEntities)
            }

             _login.value = State_Login.Success(userModel)
        }
    }

    private val _digitalSign = MutableLiveData<UserModel>()
    val digitalSign: LiveData<UserModel> = _digitalSign

    fun createDigitalSign(ds: Int) {
        viewModelScope.launch {
            val userFromDB: UserModel = userDBRepositoryImpl.findUserData()
            val userModel: UserModel = userAPIRepositoryImpl.createDigitalSign(userFromDB.id, ds)

            userDBRepositoryImpl.updateDigitalSign(userModel)

            _digitalSign.value = userModel
        }
    }

    fun resetLoginObserve() {
        this._login.value = null
    }

    private suspend fun clearDBL() {
        userDBRepositoryImpl.clearLocalUsers()
        accountDBRepositoryImpl.clearLocalAccountData()
        cardDBRepositoryImpl.clearLocalCardData()
    }
}