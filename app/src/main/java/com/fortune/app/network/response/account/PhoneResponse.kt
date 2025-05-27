package com.fortune.app.network.response.account

import com.google.gson.annotations.SerializedName

data class PhoneResponse(
    @SerializedName("phone") val phone: String
)
