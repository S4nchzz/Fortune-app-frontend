package com.fortune.app.ui.adapters.bizums

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RequestedBizumItem(
    val date: Date,
    val from: String,
    val amount: Double,
    val description: String,
)