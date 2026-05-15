package com.example.currencyconverterapp.domain.model

/**
 * Represents a currency and its exchange rate relative to a base currency.
 *
 * @param code The 3-letter ISO currency code (e.g., USD, EUR).
 * @param rateAgainstBase The exchange rate relative to the base currency (e.g., 1 USD = 74.5 INR means rateAgainstBase = 74.5 for INR).
 */
data class Currency(
    val code: String,
    val rateAgainstBase: Double
)
