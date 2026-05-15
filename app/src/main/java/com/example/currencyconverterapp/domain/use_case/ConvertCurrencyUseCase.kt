package com.example.currencyconverterapp.domain.use_case

import com.example.currencyconverterapp.domain.model.Currency
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {
    fun execute(
        amount: Double,
        selectedCurrency: String,
        rates: List<Currency>
    ): List<Pair<String, Double>> {
        val baseRate =
            rates.find { it.code == selectedCurrency }?.rateAgainstBase ?: return emptyList()
        return rates.map { currency ->
            val converted = (amount / baseRate) * currency.rateAgainstBase
            currency.code to String.format("%.2f", converted).toDouble()
        }
    }
}