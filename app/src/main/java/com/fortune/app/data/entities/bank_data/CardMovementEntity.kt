package com.fortune.app.data.entities.bank_data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CardMovementEntity (
    @SerializedName("amount") val amount: String,

    @SerializedName("date") val date: Date,

    @SerializedName("entity_receiver") val entityReceiver: String,

    @SerializedName("entity_sender") val entitySender: String

)