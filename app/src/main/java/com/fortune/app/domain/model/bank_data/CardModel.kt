package com.fortune.app.domain.model.bank_data

data class CardModel(
    val id: Int = 0,
    val cardType: String,
    val cardNumber: String,
    val expDate: String,
    val balance: Double,
    val blocked: Boolean
)