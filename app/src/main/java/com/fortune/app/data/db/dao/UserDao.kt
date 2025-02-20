package com.fortune.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fortune.app.data.db.entities.UserEntity

@Dao
abstract class UserDao {
    @Insert
    abstract fun saveUser(userEntity: UserEntity)

    @Update
    abstract fun updateDigitalSign(userEntity: UserEntity)

    @Query("SELECT * FROM fl_user")
    abstract fun findUserData(): UserEntity

    @Update
    abstract fun updateProfileStatus(userEntityUpdated: UserEntity)

    @Query("DELETE FROM fl_user")
    abstract fun clearLocalUsers()
}