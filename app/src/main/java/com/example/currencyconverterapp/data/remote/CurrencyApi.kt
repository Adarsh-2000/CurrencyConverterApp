package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.remote.dto.CurrencyRatesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest.json")
    suspend fun getLatestRates(@Query("app_id") appId: String): CurrencyRatesDto
}