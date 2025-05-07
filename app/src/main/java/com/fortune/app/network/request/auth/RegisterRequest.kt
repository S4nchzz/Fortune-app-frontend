package com.fortune.app.network.request.auth

data class RegisterRequest(
    val identityDocument: String,
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val address: String,
)