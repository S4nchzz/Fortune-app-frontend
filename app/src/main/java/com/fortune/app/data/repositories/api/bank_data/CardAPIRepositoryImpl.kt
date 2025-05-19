package com.fortune.app.data.repositories.api.bank_data

import com.fortune.app.data.mapper.bank_data.CardMapper
import com.fortune.app.data.config.api.bank_data.CardAPIRest
import com.fortune.app.data.entities.bank_data.CardEntity
import com.fortune.app.data.entities.bank_data.CardMovementEntity
import com.fortune.app.data.mapper.bank_data.CardMovementMapper
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.model.bank_data.MovementModel
import com.fortune.app.domain.repository.api.bank_Data.CardRepository
import com.fortune.app.domain.state.CardBalanceState
import com.fortune.app.domain.state.CardCvvState
import com.fortune.app.domain.state.CardExpDateState
import com.fortune.app.domain.state.CardNumberState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.CardUniqueState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState
import com.fortune.app.domain.state.MovementState
import com.fortune.app.domain.state.NewBalanceState
import com.fortune.app.network.request.account.CardUpdateAccBalanceRequest
import com.fortune.app.network.request.card.CardUUIDApiRequest
import com.fortune.app.network.request.card.TransferBalanceApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CardAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
): CardRepository {
    private val cardAPIService = retrofit.create(CardAPIRest::class.java)

    override suspend fun findCards(token: String): CardState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.findCards(token)

            if (response.code() == 200 && response.body() != null) {
                val domainCards: MutableList<CardModel> = mutableListOf()
                response.body()!!.forEach { cardEntity: CardEntity ->
                    domainCards.add(CardMapper.mapToDomain(cardEntity))
                }

                CardState.Success(domainCards)
            } else {
                CardState.Error
            }
        }
    }

    override suspend fun findMovements(token: String, card_uuid: String): MovementState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.findMovements(token, CardUUIDApiRequest(card_uuid))

            if (response.code() == 200 && response.body() != null) {
                val domainMovement: MutableList<MovementModel> = mutableListOf()
                response.body()!!.forEach { movementEntity : CardMovementEntity ->
                    domainMovement.add(CardMovementMapper.mapToDomain(movementEntity))
                }

                MovementState.Success(domainMovement)
            } else {
                MovementState.Error
            }
        }
    }

    override suspend fun lockCard(token: String, cardUuid: String): LockCardState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.lockCard(token, CardUUIDApiRequest(cardUuid))

            if (response.code() == 200 && response.body() != null) {
                LockCardState.Success(response.body()!!.locked)
            } else {
                LockCardState.Error
            }
        }
    }

    override suspend fun isCardLocked(token: String, cardUuid: String): LockCardState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.isCardLocked(token, CardUUIDApiRequest(cardUuid))
            if (response.code() == 200 && response.body() != null) {
                LockCardState.Success(response.body()!!.locked)
            } else {
                LockCardState.Error
            }
        }
    }

    override suspend fun getCardNumber(token: String, card_uuid: String): CardNumberState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.getCardNumber(token, CardUUIDApiRequest(card_uuid))

            if (response.code() == 200 && response.body() != null) {
                CardNumberState.Success(response.body()!!.cardNumber)
            } else {
                CardNumberState.Error
            }
        }
    }

    override suspend fun getExpDate(token: String, card_uuid: String): CardExpDateState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.getExpDate(token, CardUUIDApiRequest(card_uuid))

            if (response.code() == 200 && response.body() != null) {
                CardExpDateState.Success(response.body()!!.cardExpDate)
            } else {
                CardExpDateState.Error
            }
        }
    }

    override suspend fun getCvv(token: String, card_uuid: String): CardCvvState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.getCvv(token, CardUUIDApiRequest(card_uuid))

            if (response.code() == 200 && response.body() != null) {
                CardCvvState.Success(response.body()!!.card_cvv)
            } else {
                CardCvvState.Error
            }
        }
    }

    override suspend fun getCardBalance(token: String, card_uuid: String): CardBalanceState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.getBalance(token, CardUUIDApiRequest(card_uuid))

            if (response.code() == 200 && response.body() != null) {
                CardBalanceState.Success(response.body()!!.card_balance)
            } else {
                CardBalanceState.Error
            }
        }
    }

    override suspend fun addNewBalance(newBalance: Double, token: String): NewBalanceState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.addNewBalance(token, CardUpdateAccBalanceRequest(newBalance))

            if (response.code() == 200 && response.body() != null) {
                NewBalanceState.Success(response.body()!!.balanceUpdated)
            } else {
                NewBalanceState.Error
            }
        }
    }

    override suspend fun addNewCard(token: String): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.addNewCard(token)

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }

    override suspend fun getCardByUUID(token: String, uuid: String): CardUniqueState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.getCardByUUID(token, CardUUIDApiRequest(uuid))

            if (response.code() == 200 && response.body() != null) {
                CardUniqueState.Success(response.body()!!)
            } else {
                CardUniqueState.Error
            }
        }
    }

    override suspend fun transferBalance(token: String, fromCardUUID: String, toCardUUID: String, balance: Double): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = cardAPIService.transferBalance(token, TransferBalanceApiRequest(fromCardUUID, toCardUUID, balance))

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }
}