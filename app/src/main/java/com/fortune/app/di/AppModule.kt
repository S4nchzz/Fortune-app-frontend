package com.fortune.app.di

import android.content.Context
import androidx.room.Room
import com.fortune.app.BuildConfig
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
    fun provideUserAPIRepository(retrofit: Retrofit): UserAPIRepositoryImpl {
        return UserAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideUProfileAPI(retrofit: Retrofit): UProfileAPIRepositoryImpl {
        return UProfileAPIRepositoryImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideCardAPIRepository(retrofit: Retrofit): CardAPIRepositoryImpl {
        return CardAPIRepositoryImpl(retrofit)
    }


    @Provides
    @Singleton
    fun provideAccountAPIRepository(retrofit: Retrofit): AccountAPIRepositoryImpl {
        return AccountAPIRepositoryImpl(retrofit)
    }
}