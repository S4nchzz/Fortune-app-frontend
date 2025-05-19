package com.fortune.app.network.request.card

data class TransferBalanceApiRequest(
    val fromUUID: String,
    val toUUID: String,
    val balance: Double
)