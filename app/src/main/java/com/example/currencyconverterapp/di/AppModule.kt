package com.example.currencyconverterapp.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverterapp.common.ApiConstants
import com.example.currencyconverterapp.common.DatabaseConstants
import com.example.currencyconverterapp.data.local.CurrencyDao
import com.example.currencyconverterapp.data.local.CurrencyDatabase
import com.example.currencyconverterapp.data.remote.CurrencyApi
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
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
    fun provideBaseUrl() = ApiConstants.BASE_URL

    @Provides
    @Singleton
    fun provideCurrencyApi(baseUrl: String): CurrencyApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context, CurrencyDatabase::class.java, DatabaseConstants.CURRENCY_DB
        ).build()
    }

    @Provides
    fun provideDao(db: CurrencyDatabase) = db.currencyDao()

    @Provides
    fun provideRepository(
        api: CurrencyApi, dao: CurrencyDao
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(api, dao)
    }
}