package com.fortune.app.data.secure

import android.app.Application
import android.content.Context
import javax.inject.Inject

class TokenManager @Inject constructor(val application: Application) {
    fun saveToken(token: String) {
        val sharedPrefs = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        val sharedPrefs = application.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getString("auth_token", null)
    }
}