package com.fortune.app.data.repositories.db.bank_data

import android.util.Log
import com.fortune.app.data.config.db.AppDatabase
import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.data.entities.bank_data.AccountMapper
import com.fortune.app.domain.model.bank_data.AccountModel
import com.fortune.app.domain.repository.db.bank_Data.AccountDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountDBRepositoryImpl @Inject constructor(
    val appDatabase: AppDatabase
) : AccountDBRepository {
    override suspend fun saveAccount(accountModel: AccountModel) {
        withContext(Dispatchers.IO) {
            appDatabase.accountDao().saveAccount(AccountMapper.mapToEntity(accountModel))
        }
    }

    override suspend fun clearLocalAccountData() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.accountDao().clearLocalAccounts()
            } catch (e: Exception) {
                Log.e("AccountDeleteData", "Not data found to remove")
            }
        }
    }
}