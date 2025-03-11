package com.fortune.app.data.config.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fortune.app.data.config.db.dao.bank_data.AccountDAO
import com.fortune.app.data.config.db.dao.bank_data.CardDAO
import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.config.db.dao.user.UProfileDao
import com.fortune.app.data.config.db.dao.user.UserDao
import com.fortune.app.data.entities.bank_data.AccountEntity
import com.fortune.app.data.entities.bank_data.CardEntity

@Database(entities = [UserEntity::class, UProfileEntity::class, AccountEntity::class, CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun uProfileDao(): UProfileDao
    abstract fun accountDao(): AccountDAO
    abstract fun cardDao(): CardDAO
}