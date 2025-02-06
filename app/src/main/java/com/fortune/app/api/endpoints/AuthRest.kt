package com.fortune.app.api.endpoints

import com.fortune.app.BuildConfig
import com.fortune.app.api.modals.auth.LoginCredentialsResponseModal
import com.fortune.app.api.modals.auth.UserModal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRest {
    @GET("${BuildConfig.API_URL}/user/login")
    fun login(@Query("dni_nie") dni_nie: String, @Query("password") password: String): Call<UserModal>

    @POST("${BuildConfig.API_URL}/user/register")
    fun register(@Query("dni_nie") dni_nie: String, @Query("email") email: String, @Query("password") password: String): Call<UserModal>
}