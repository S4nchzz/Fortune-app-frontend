package com.fortune.app.di

import android.content.Context
import androidx.room.Room
import com.fortune.app.BuildConfig
import com.fortune.app.data.db.AppDatabase
import com.fortune.app.data.repositories.db.user.UProfileDBRepository
import com.fortune.app.data.repositories.db.user.UserDBRepository
import com.fortune.app.data.repositories.remote.user.UProfileAPIRepository
import com.fortune.app.data.repositories.remote.user.UserAPIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "fortune_dbl").build()
    }

    @Provides
    @Singleton
    fun provideUserAPIRepository(retrofit: Retrofit): UserAPIRepository {
        return UserAPIRepository(retrofit)
    }

    @Provides
    @Singleton
    fun provideUserDBRepository(appDatabase: AppDatabase): UserDBRepository {
        return UserDBRepository(appDatabase)
    }

    @Provides
    @Singleton
    fun provideUProfileAPI(retrofit: Retrofit): UProfileAPIRepository {
        return UProfileAPIRepository(retrofit)
    }

    @Provides
    @Singleton
    fun provideUProfileDBRepository(appDatabase: AppDatabase): UProfileDBRepository {
        return UProfileDBRepository(appDatabase)
    }
}