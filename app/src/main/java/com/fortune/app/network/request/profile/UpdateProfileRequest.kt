package com.fortune.app.network.request.profile

data class UpdateProfileRequest(
    val name: String,
    val address: String,
    val identityDocument: String,
    val email: String,
    val phone: String
)
