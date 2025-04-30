package com.fortune.app.domain.model.bank_data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CardMovementModel (
    val amount: String,
    val date: Date,
    val entityReceiver: String,
    val entitySender: String
)