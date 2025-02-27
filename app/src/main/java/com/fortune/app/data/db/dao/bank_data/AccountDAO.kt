package com.fortune.app.data.db.dao.bank_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fortune.app.data.entities.bank_data.AccountEntity

@Dao
abstract class AccountDAO {
    @Insert
    abstract fun saveAccount(accountEntity: AccountEntity)

    @Query("DELETE FROM fl_account")
    abstract fun clearLocalAccounts()
}