package com.fortune.app.data.entities.bank_data

import com.fortune.app.domain.model.bank_data.AccountModel

object AccountMapper {
    fun mapToDomain(accountEntity: AccountEntity): AccountModel {
        return AccountModel(
            id = accountEntity.id,
            accountId = accountEntity.account_id,
            proprietaryId = accountEntity.proprietary_id,
            totalBalance = accountEntity.totalBalance
        )
    }

    fun mapToEntity(accountModel: AccountModel): AccountEntity {
        return AccountEntity(
            id = accountModel.id,
            account_id = accountModel.accountId,
            proprietary_id = accountModel.proprietaryId,
            totalBalance = accountModel.totalBalance
        )
    }
}