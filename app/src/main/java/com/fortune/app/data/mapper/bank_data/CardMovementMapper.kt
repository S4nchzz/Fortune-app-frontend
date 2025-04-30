package com.fortune.app.data.mapper.bank_data

import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.domain.model.bank_data.CardMovementModel

object CardMovementMapper {
    fun mapToDomain(cardMovementEntity: CardMovementEntity): CardMovementModel {
        return CardMovementModel(
            amount = cardMovementEntity.amount,
            date = cardMovementEntity.date,
            entityReceiver = cardMovementEntity.entityReceiver,
            entitySender = cardMovementEntity.entitySender
        )
    }

    fun mapToEntity(cardMovementModel: CardMovementModel): CardMovementEntity {
        return CardMovementEntity(
            amount = cardMovementModel.amount,
            date = cardMovementModel.date,
            entityReceiver = cardMovementModel.entityReceiver,
            entitySender = cardMovementModel.entitySender
        )
    }
}
