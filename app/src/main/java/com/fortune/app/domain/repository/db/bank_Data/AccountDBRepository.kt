package com.fortune.app.domain.repository.db.bank_Data

import com.fortune.app.domain.model.bank_data.AccountModel

interface AccountDBRepository {
    suspend fun saveAccount(accountModel: AccountModel)
    suspend fun clearLocalAccountData()
}