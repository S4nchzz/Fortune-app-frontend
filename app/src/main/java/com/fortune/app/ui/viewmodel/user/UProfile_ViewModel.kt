package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.db.entities.UProfileEntity
import com.fortune.app.data.db.entities.UserEntity
import com.fortune.app.data.repositories.remote.user.UProfileAPIRepository
import com.fortune.app.data.repositories.db.user.UProfileDBRepository
import com.fortune.app.data.repositories.db.user.UserDBRepository
import com.fortune.app.data.repositories.remote.user.UserAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UProfile_ViewModel @Inject constructor(
    private val uProfileAPIRepository: UProfileAPIRepository,
    private val uProfileDBRepository: UProfileDBRepository,
    private val userAPIRepository: UserAPIRepository,
    private val userDBRepository: UserDBRepository
) : ViewModel() {
    private val _profile = MutableLiveData<UProfileEntity>()
    val profile: LiveData<UProfileEntity> = _profile

    fun createProfile(name: String, address: String, phone: String) {
        viewModelScope.launch {
            val user: UserEntity = userDBRepository.findUserData()
            val entityApiSend = UProfileEntity(
                user_id = user.id,
                name = name,
                address = address,
                phone = phone,
                online = false
            )

            val entityResponseFromAPI = uProfileAPIRepository.createProfile(entityApiSend)
            uProfileDBRepository.saveUprofile(entityResponseFromAPI)

            user.isProfileCreated = true

            val userEntityUpdated: UserEntity =
                userAPIRepository.updateProfileCreationStatus(user)
            userDBRepository.updateProfileStatus(userEntityUpdated)

            _profile.value = entityResponseFromAPI
        }
    }

    fun migrateProfileFromAPI(id: Long) {
        viewModelScope.launch {
            val uProfileMigration = uProfileAPIRepository.findProfileByUserId(id)
            uProfileDBRepository.saveUprofile(uProfileMigration)

            _profile.value = uProfileMigration
        }
    }

    fun clearLocalProfiles() {
        viewModelScope.launch {
            uProfileDBRepository.clearLocalProfiles()
        }
    }
}