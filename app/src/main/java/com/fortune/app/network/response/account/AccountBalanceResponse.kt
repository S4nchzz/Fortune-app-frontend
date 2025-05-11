package com.fortune.app.network.response.account

import com.google.gson.annotations.SerializedName

data class AccountBalanceResponse(
    @SerializedName("accountBalance")
    val accountBalance: Double
)
