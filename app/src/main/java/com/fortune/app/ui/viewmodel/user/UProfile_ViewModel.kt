package com.fortune.app.ui.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.repositories.remote.user.UProfileAPIRepositoryImpl
import com.fortune.app.data.repositories.db.user.UProfileDBRepositoryImpl
import com.fortune.app.data.repositories.db.user.UserDBRepositoryImpl
import com.fortune.app.data.repositories.remote.user.UserAPIRepositoryImpl
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.model.user.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UProfile_ViewModel @Inject constructor(
    private val uProfileAPIRepositoryImpl: UProfileAPIRepositoryImpl,
    private val uProfileDBRepositoryImpl: UProfileDBRepositoryImpl,
    private val userAPIRepositoryImpl: UserAPIRepositoryImpl,
    private val userDBRepositoryImpl: UserDBRepositoryImpl
) : ViewModel() {
    private val _profile = MutableLiveData<UProfileModel>()
    val profile: LiveData<UProfileModel> = _profile

    fun createProfile(uProfileDTO: UProfileDTO?) {
        viewModelScope.launch {
            if (uProfileDTO != null) {
                val user: UserModel = userDBRepositoryImpl.findUserData()

                val modelResponseFromAPI = uProfileAPIRepositoryImpl.createProfile(user.id, uProfileDTO.name, uProfileDTO.address, uProfileDTO.phone, false)
                uProfileDBRepositoryImpl.saveUprofile(modelResponseFromAPI)

                user.is_profile_created = true

                val userModelUpdate: UserModel =
                    userAPIRepositoryImpl.updateProfileCreationStatus(user)
                userDBRepositoryImpl.updateProfileStatus(userModelUpdate)

                _profile.value = modelResponseFromAPI
            }
        }
    }

    fun findLocalProfileData() {
        viewModelScope.launch {
            _profile.value = uProfileDBRepositoryImpl.findProfile()
        }
    }
}