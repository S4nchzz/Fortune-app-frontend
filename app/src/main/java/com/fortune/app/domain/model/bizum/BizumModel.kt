package com.fortune.app.domain.model.bizum

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BizumModel (
    val amount: String,

    val date: Date,

    val description: String,

    val from: String,

    val to: String
)