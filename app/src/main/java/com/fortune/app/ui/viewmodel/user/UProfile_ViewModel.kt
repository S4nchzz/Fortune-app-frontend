package com.fortune.app.ui.viewmodel.user

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.repositories.api.user.UProfileAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UserAPIRepositoryImpl
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.domain.model.user.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UProfile_ViewModel @Inject constructor(
    private val uProfileAPIRepositoryImpl: UProfileAPIRepositoryImpl,
    private val userAPIRepositoryImpl: UserAPIRepositoryImpl,
    private val application: Application
) : ViewModel() {
    private val _profile = MutableLiveData<UProfileModel>()
    val profile: LiveData<UProfileModel> = _profile

    fun createProfile(uProfileDTO: UProfileDTO?) {
        viewModelScope.launch {
            if (uProfileDTO != null) {
                val sharedPreferences = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", null)

                val modelResponseFromAPI = uProfileAPIRepositoryImpl.createProfile("Bearer ${token.toString()}", uProfileDTO.name, uProfileDTO.address, uProfileDTO.phone, false)

                _profile.value = modelResponseFromAPI
            }
        }
    }
}