package com.fortune.app.api.modals.auth

import com.google.gson.annotations.SerializedName

data class UserModal(@SerializedName("id") val id: Long,
                     @SerializedName("nif_nie") val nifnie: String,
                     @SerializedName("email") val email: String,
                     @SerializedName("password") val password: String,
                     @SerializedName("digital_sign") val digitalSign: Int?)