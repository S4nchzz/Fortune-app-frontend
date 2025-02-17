package com.fortune.app.api.endpoints

import com.fortune.app.BuildConfig
import com.fortune.app.api.modals.auth.UserProfileModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.POST

interface UserProfileRest {
    @POST("`${BuildConfig.API_URL}` + /userprofile/generateProfile")
    fun userProfile(id: Long, name: String, address: String, telf: String): Call<UserProfileModal>
}