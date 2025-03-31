package com.fortune.app.network.response.auth

data class LoginResponse(val token: String, val hasDigitalSign: Boolean)