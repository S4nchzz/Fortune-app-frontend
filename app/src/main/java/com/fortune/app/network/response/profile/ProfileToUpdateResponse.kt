package com.fortune.app.network.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileToUpdateResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("identity_document")
    val identityDocument: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String
)
