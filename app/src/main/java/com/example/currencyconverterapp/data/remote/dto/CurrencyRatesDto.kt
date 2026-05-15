package com.example.currencyconverterapp.data.remote.dto

data class CurrencyRatesDto(
    val disclaimer: String? = null,
    val license: String? = null,
    val timestamp: Long? = null,
    val base: String? = null,
    val rates: Map<String, Double>? = null
)