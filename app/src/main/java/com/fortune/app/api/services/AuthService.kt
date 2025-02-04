package com.fortune.app.api.services

import com.fortune.app.api.RetroFitInstance
import com.fortune.app.api.endpoints.AuthRest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    private val retroFit = RetroFitInstance.retroFit.create(AuthRest::class.java)

    fun login(): Boolean {
        retroFit.login().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // Handle successful response
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Handle call failure
            }
        })

        return true
    }
}