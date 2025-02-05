package com.fortune.app.api.modals.login

import com.google.gson.annotations.SerializedName

data class LoginCredentialsResponseModal(@SerializedName("login_credentials") val loginCredential: Boolean, @SerializedName("hasDigitalSign") val hasDigitalSign: Boolean)
