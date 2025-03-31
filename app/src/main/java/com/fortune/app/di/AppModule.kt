package com.fortune.app.di

import android.content.Context
import androidx.room.Room
import com.fortune.app.BuildConfig
import com.fortune.app.data.config.db.AppDatabase
import com.fortune.app.data.repositories.db.bank_data.AccountDBRepositoryImpl
import com.fortune.app.data.repositories.db.bank_data.CardDBRepositoryImpl
import com.fortune.app.data.repositories.db.user.UProfileDBRepositoryImpl
import com.fortune.app.data.repositories.db.user.UserDBRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UProfileAPIRepositoryImpl
import com.fortune.app.data.repositories.api.user.UserAPIRepositoryImpl
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
    fun provideUserAPIRepository(retrofit: Retrofit): UserAPIRepositoryImpl {
        return UserAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideUserDBRepository(appDatabase: AppDatabase): UserDBRepositoryImpl {
        return UserDBRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideUProfileAPI(retrofit: Retrofit): UProfileAPIRepositoryImpl {
        return UProfileAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideUProfileDBRepository(appDatabase: AppDatabase): UProfileDBRepositoryImpl {
        return UProfileDBRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideCardAPIRepository(retrofit: Retrofit): CardAPIRepositoryImpl {
        return CardAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideCardDBRepository(appDatabase: AppDatabase): CardDBRepositoryImpl {
        return CardDBRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideAccountAPIRepository(retrofit: Retrofit): AccountAPIRepositoryImpl {
        return AccountAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideAccountDBRepository(appDatabase: AppDatabase): AccountDBRepositoryImpl {
        return AccountDBRepositoryImpl(appDatabase)
    }
}