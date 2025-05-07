package com.fortune.app.domain.state

sealed class PaymentSimulationState {
    data class Success(val paymentSimulated: Boolean): PaymentSimulationState()
    object Error: PaymentSimulationState()
}