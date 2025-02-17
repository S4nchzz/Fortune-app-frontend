package com.fortune.app.api.services

import com.fortune.app.api.RetroFitInstance
import com.fortune.app.api.endpoints.UserProfileRest
import com.fortune.app.api.modals.auth.UserProfileModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserProfileService {
    private val retroFit = RetroFitInstance.retroFit.create(UserProfileRest::class.java)

    fun generateProfile(
        id: Long, name: String, address: String, telf: String, callOnResponse: (UserProfileModal) -> Unit) {
        retroFit.userProfile(id, name, address, telf).enqueue(object : Callback<UserProfileModal> {
            override fun onResponse(
                call: Call<UserProfileModal>,
                response: Response<UserProfileModal>
            ) {
                // Response true -> callback con el profile
            }

            override fun onFailure(call: Call<UserProfileModal>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}