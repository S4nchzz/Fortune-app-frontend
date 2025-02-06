package com.fortune.app.api.services

import android.util.Log
import com.fortune.app.api.RetroFitInstance
import com.fortune.app.api.endpoints.AuthRest
import com.fortune.app.api.modals.auth.UserModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    private val retroFit = RetroFitInstance.retroFit.create(AuthRest::class.java)

    fun login(dni_nie: String, password: String, callBack: (UserModal?) -> Unit) {
        retroFit.login(dni_nie.uppercase(), password).enqueue(object : Callback<UserModal> {
            override fun onResponse(
                call: Call<UserModal>,
                response: Response<UserModal>
            ) {
                if (response.isSuccessful) {
                    Log.i("LOGIN_RESPONSE_SUCCESFUL", response.body().toString())

                    callBack(response.body())
                } else {
                    callBack(null)
                }
            }

            override fun onFailure(call: Call<UserModal>, t: Throwable) {
                Log.e("LOGIN_RESPONSE_FAILURE", t.toString())
                callBack(null)
            }
        })
    }

    fun register(dni_nie: String, email: String, password: String, callBack: (UserModal?) -> Unit) {
        retroFit.register(dni_nie.uppercase(), email, password).enqueue(object: Callback<UserModal> {
            override fun onResponse(
                call: Call<UserModal>,
                response: Response<UserModal>
            ) {
                Log.i("REGISTER_RESPONSE_STATUS", "Register response recived")
                callBack(response.body())
            }

            override fun onFailure(call: Call<UserModal>, t: Throwable) {
                Log.e("REGISTER_RESPONSE_STATUS", t.toString())
                callBack(null)
            }
        })
    }
}