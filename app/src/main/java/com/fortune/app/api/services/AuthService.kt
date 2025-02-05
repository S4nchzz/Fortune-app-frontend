package com.fortune.app.api.services

import android.util.Log
import com.fortune.app.api.RetroFitInstance
import com.fortune.app.api.endpoints.AuthRest
import com.fortune.app.api.modals.login.LoginCredentialsResponseModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

object AuthService {
    private val retroFit = RetroFitInstance.retroFit.create(AuthRest::class.java)

    fun login(nif_nie: String, password: String, callBack: (Boolean, Boolean) -> Unit) {
        retroFit.login(nif_nie, password).enqueue(object : Callback<LoginCredentialsResponseModal> {
            override fun onResponse(
                call: Call<LoginCredentialsResponseModal>,
                response: Response<LoginCredentialsResponseModal>
            ) {
                if (response.isSuccessful) {
                    Log.i("LOGIN_RESPONSE_SUCCESFUL", response.body().toString())

                    val loginCredential = response.body()?.loginCredential ?: false
                    val hasDigitalSign = response.body()?.hasDigitalSign ?: false
                    callBack(loginCredential, hasDigitalSign)
                } else {
                    callBack(false, false)
                }
            }

            override fun onFailure(call: Call<LoginCredentialsResponseModal>, t: Throwable) {
                Log.e("LOGIN_RESPONSE_FAILURE", t.toString())
                callBack(false, false)
            }

        })
    }
}