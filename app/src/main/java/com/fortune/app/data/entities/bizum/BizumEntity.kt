package com.fortune.app.data.entities.bizum

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BizumEntity (
    @SerializedName("amount")
    val amount: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("description")
    val description: String,

    @SerializedName("_from")
    val from: String,

    @SerializedName("_to")
    val to: String
)