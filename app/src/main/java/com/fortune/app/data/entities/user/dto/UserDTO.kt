package com.fortune.app.data.entities.user.dto

import java.io.Serializable

data class UserDTO(
    val identityDocument: String,
    val email: String,
    val password: String
) : Serializable
