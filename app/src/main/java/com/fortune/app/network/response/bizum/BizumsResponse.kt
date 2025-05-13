package com.fortune.app.network.response.bizum

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BizumsResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("date")
    val date: Date,

    @SerializedName("from")
    val from: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("description")
    val description: String,

    @SerializedName("amountIn")
    val amountIn: Boolean
)
