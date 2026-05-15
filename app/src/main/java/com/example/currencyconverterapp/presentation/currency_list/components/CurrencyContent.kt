package com.example.currencyconverterapp.presentation.currency_list.components

import androidx.compose.runtime.Composable
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyConverterState

/**
 * Displays the main content of the currency converter based on the current state.
 * It shows a loading indicator, error message, empty view, or the list of currency rates.
 *
 * @param state The current state of the CurrencyConverter.
 */
@Composable
fun CurrencyContent(state: CurrencyConverterState) {
    when {
        state.isLoading -> LoadingState()
        state.error != null -> ErrorState(state.error)
        state.rates.isEmpty() -> EmptyState()
        else -> CurrencyGrid(rates = state.rates)
    }
}