package com.fortune.app.data.repositories.db.bank_data

import android.util.Log
import com.fortune.app.data.db.AppDatabase
import com.fortune.app.data.entities.bank_data.AccountEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountDBRepository @Inject constructor(
    val appDatabase: AppDatabase
) {
    suspend fun saveAccount(accountEntity: AccountEntity) {
        withContext(Dispatchers.IO) {
            appDatabase.accountDao().saveAccount(accountEntity)
        }
    }

    suspend fun clearLocalAccountData() {
        withContext(Dispatchers.IO) {
            try {
                appDatabase.accountDao().clearLocalAccounts()
            } catch (e: Exception) {
                Log.e("AccountDeleteData", "Not data found to remove")
            }
        }
    }
}