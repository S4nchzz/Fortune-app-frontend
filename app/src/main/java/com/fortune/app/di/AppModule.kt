package com.fortune.app.di

import android.app.Application
import com.fortune.app.BuildConfig
import com.fortune.app.data.repositories.api.bank_data.AccountAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bank_data.CardAPIRepositoryImpl
import com.fortune.app.data.repositories.api.auth.AuthAPIRepositoryImpl
import com.fortune.app.data.repositories.api.bizum.BizumAPIRepositoryImpl
import com.fortune.app.data.secure.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideUserAPIRepository(retrofit: Retrofit): AuthAPIRepositoryImpl {
        return AuthAPIRepositoryImpl(retrofit)
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

    @Provides
    @Singleton
    fun provideTokenMaanger(application: Application): TokenManager {
        return TokenManager(application)
    }

    @Provides
    @Singleton
    fun provideBizumAPIRepository(retrofit: Retrofit): BizumAPIRepositoryImpl {
        return BizumAPIRepositoryImpl(retrofit)
    }
}