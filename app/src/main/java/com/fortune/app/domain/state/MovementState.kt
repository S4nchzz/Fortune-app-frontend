package com.fortune.app.domain.state

import com.fortune.app.domain.model.bank_data.MovementModel

sealed class MovementState {
    data class Success(val movementModel: List<MovementModel>): MovementState()
    object Error: MovementState()
}
