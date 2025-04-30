package com.fortune.app.domain.model.bank_data

data class CardModel(
    val uuid: String,
    val cardType: String,
    val cardNumber: String,
    val expDate: String,
    val balance: Double,
    val blocked: Boolean
)