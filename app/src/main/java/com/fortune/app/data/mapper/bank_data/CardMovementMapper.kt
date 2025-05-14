package com.fortune.app.data.mapper.bank_data

import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.domain.model.bank_data.MovementModel

object CardMovementMapper {
    fun mapToDomain(cardMovementEntity: CardMovementEntity): MovementModel {
        return MovementModel(
            amount = cardMovementEntity.amount,
            date = cardMovementEntity.date,
            entityReceiver = cardMovementEntity.entityReceiver,
            entitySender = cardMovementEntity.entitySender
        )
    }

    fun mapToEntity(movementModel: MovementModel): CardMovementEntity {
        return CardMovementEntity(
            amount = movementModel.amount,
            date = movementModel.date,
            entityReceiver = movementModel.entityReceiver,
            entitySender = movementModel.entitySender
        )
    }
}
