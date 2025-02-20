package com.fortune.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fortune.app.data.db.entities.UProfileEntity

@Dao
abstract class UProfileDao {
    @Insert
    abstract fun saveUProfile(entityResponseFromAPI: UProfileEntity)

    @Query("SELECT * FROM fl_user_profile")
    abstract fun findProfile(): UProfileEntity?

    @Query("DELETE FROM fl_user")
    abstract fun clearLocalProfiles()
}