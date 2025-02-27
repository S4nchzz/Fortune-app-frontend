package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.entities.user.dto.UProfileDTO
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

    fun createProfile(uProfileDTO: UProfileDTO?) {
        viewModelScope.launch {
            if (uProfileDTO != null) {
                val user: UserEntity = userDBRepository.findUserData()

                val entityResponseFromAPI = uProfileAPIRepository.createProfile(user.id, uProfileDTO.name, uProfileDTO.address, uProfileDTO.phone, false)
                uProfileDBRepository.saveUprofile(entityResponseFromAPI)

                user.isProfileCreated = true

                val userEntityUpdated: UserEntity =
                    userAPIRepository.updateProfileCreationStatus(user)
                userDBRepository.updateProfileStatus(userEntityUpdated)

                _profile.value = entityResponseFromAPI
            }
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

    fun findLocalProfileData() {
        viewModelScope.launch {
            _profile.value = uProfileDBRepository.findProfile()
        }
    }
}