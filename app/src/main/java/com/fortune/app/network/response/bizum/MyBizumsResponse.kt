package com.fortune.app.network.response.bizum

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MyBizumsResponse(
    @SerializedName("date")
    val date: Date,

    @SerializedName("to")
    val to: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("description")
    val description: String,

    @SerializedName("amountIn")
    val amountIn: Boolean)
