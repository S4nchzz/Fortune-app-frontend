package com.fortune.app.domain.model.bank_data

import java.util.Date

data class MovementModel (
    val amount: String,
    val date: Date,
    val entityReceiver: String,
    val entitySender: String
)