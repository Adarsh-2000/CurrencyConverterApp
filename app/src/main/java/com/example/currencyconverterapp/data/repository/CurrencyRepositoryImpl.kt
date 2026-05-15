package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.common.ApiConstants.APP_ID
import com.example.currencyconverterapp.data.local.CurrencyDao
import com.example.currencyconverterapp.data.local.CurrencyEntity
import com.example.currencyconverterapp.data.remote.CurrencyApi
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi,
    private val dao: CurrencyDao
) : CurrencyRepository {

    override suspend fun getRemoteRates(): Map<String, Double>? {
        return try {
            api.getLatestRates(APP_ID).rates
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getLocalRates(): List<Currency> {
        return dao.getAllRates().map { Currency(it.currency, it.rateAgainstBase) }
    }

    override suspend fun saveRates(rates: Map<String, Double>) {
        val entities = rates.map { CurrencyEntity(it.key, it.value) }
        dao.clearRates()
        dao.insertAll(entities)
    }

    override suspend fun clearRates() {
        dao.clearRates()
    }
}
