package com.fortune.app.api.modals.auth

import com.google.gson.annotations.SerializedName

data class UserModal(@SerializedName("id") val id: Long,
                     @SerializedName("dni_nie") val dniNie: String,
                     @SerializedName("email") val email: String,
                     @SerializedName("digital_sign") val digitalSign: Int?)