package com.fortune.app.api.endpoints

import com.fortune.app.BuildConfig
import com.fortune.app.api.entity.User
import retrofit2.Call
import retrofit2.http.GET

interface AuthRest {
    @GET(BuildConfig.API_URL + "/user/login")
    fun login(): Call<String>
}