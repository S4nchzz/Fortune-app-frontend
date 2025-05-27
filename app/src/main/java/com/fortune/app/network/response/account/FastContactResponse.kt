package com.fortune.app.network.response.account

import com.google.gson.annotations.SerializedName

data class FastContactResponse(
    @SerializedName("pfp") val pfp: String,
    @SerializedName("name") val name: String,
    @SerializedName("to_id") val to_id: Long
)