package com.fortune.app.network.request.auth

data class LoginRequest(
    val identityDocument: String,
    val password: String
)
