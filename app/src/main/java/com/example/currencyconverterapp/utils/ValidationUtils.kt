package com.example.currencyconverterapp.utils


object ValidationUtils {
    /**
     * Checks if the given amount string represents an invalid currency amount.
     * An amount is considered invalid if it's empty, cannot be parsed to a Double,
     * or is less than or equal to 0.0.
     */
    fun isInvalidAmount(amount: String): Boolean {
        if (amount.isEmpty()) return true
        return amount.toDoubleOrNull() == null || amount.toDouble() <= 0.0
    }

    /**
     * Filters input to ensure it matches a valid numeric pattern for currency amounts.
     * Returns the filtered string if valid, otherwise null.
     */
    fun filterNumericInput(newValue: String): String? {
        val validNumberRegex = "^\\d*\\.?\\d*$".toRegex()
        return if (newValue.matches(validNumberRegex)) newValue else null
    }
}