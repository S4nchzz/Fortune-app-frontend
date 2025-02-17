package com.fortune.app.api.modals.auth

import com.google.gson.annotations.SerializedName

data class UserProfileModal(
    @SerializedName("ID") val id: Int,
    @SerializedName("ONLINE") val online: Boolean,
    @SerializedName("USER_ID") val userId: Int,
    @SerializedName("NAME") val name: String,
    @SerializedName("ADDRESS") val address: String,
    @SerializedName("TELF") val phone: String,
    //@SerializedName("PFP") val profilePicture: ByteArray
)
