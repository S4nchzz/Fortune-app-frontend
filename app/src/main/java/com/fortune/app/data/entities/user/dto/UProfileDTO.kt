package com.fortune.app.data.entities.user.dto

import java.io.Serializable

data class UProfileDTO(
    val name: String,
    val address: String,
    val phone: String
) : Serializable