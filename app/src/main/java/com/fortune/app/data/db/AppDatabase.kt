package com.fortune.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fortune.app.data.db.entities.UProfileEntity
import com.fortune.app.data.db.entities.UserEntity
import com.fortune.app.data.db.dao.UProfileDao
import com.fortune.app.data.db.dao.UserDao

@Database(entities = [UserEntity::class, UProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun uProfileDao(): UProfileDao
}