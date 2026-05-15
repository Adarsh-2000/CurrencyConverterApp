package com.example.currencyconverterapp.domain.repository

import com.example.currencyconverterapp.domain.model.Currency

interface CurrencyRepository {
    suspend fun getRemoteRates(): Map<String, Double>?
    suspend fun getLocalRates(): List<Currency>
    suspend fun saveRates(rates: Map<String, Double>)
    suspend fun clearRates()
}
