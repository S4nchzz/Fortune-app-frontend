package com.fortune.app.data.mapper.bank_data

import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.domain.model.bank_data.CardModel

object CardMapper {
    fun mapToDomain(cardEntity: CardEntity): CardModel {
        return CardModel(
            uuid = cardEntity.cardUUID,
            cardType = cardEntity.cardType,
            cardNumber = cardEntity.card_number,
            expDate = cardEntity.expDate,
            balance = cardEntity.balance,
            blocked = cardEntity.blocked,
            cvv = cardEntity.cvv,
            pin = cardEntity.pin
        )
    }

    fun mapToEntity(cardModel: CardModel): CardEntity {
        return CardEntity(
            cardUUID = cardModel.uuid,
            cardType = cardModel.cardType,
            card_number = cardModel.cardNumber,
            expDate = cardModel.expDate,
            balance = cardModel.balance,
            blocked = cardModel.blocked,
            cvv = cardModel.cvv,
            pin = cardModel.pin
        )
    }
}