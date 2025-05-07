package com.fortune.app.network.request.movement

data class SimulatePaymentRequest(
    val amount: Double,
    val receptorEntity: String,
    val cardUUID: String
)