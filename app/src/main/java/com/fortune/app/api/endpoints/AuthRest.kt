package com.fortune.app.api.endpoints

import com.fortune.app.BuildConfig
import com.fortune.app.api.modals.login.LoginCredentialsResponseModal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthRest {
    @GET("${BuildConfig.API_URL}/user/login")
    fun login(@Query("nif_nie") nif_nie: String, @Query("password") password: String): Call<LoginCredentialsResponseModal>
}