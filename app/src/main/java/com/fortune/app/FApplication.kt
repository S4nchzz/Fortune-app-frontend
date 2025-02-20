package com.fortune.app

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        clearDatabase()
    }

    private fun clearDatabase() {
        val dbFile = this.getDatabasePath("fortune_dbl")

        if(dbFile.exists()) {
            dbFile.delete()
        }
    }
}