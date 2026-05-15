package com.example.currencyconverterapp.presentation.currency_list.state

sealed class CurrencyEvents {
    data class OnAmountChange(val amount: String) : CurrencyEvents()
    data class OnCurrencySelected(val currency: String) : CurrencyEvents()
    data class OnSearchQueryChange(val query: String) : CurrencyEvents()
    data class OnExpandedChange(val expanded: Boolean) : CurrencyEvents()
    data class OnBottomSheetVisibilityChange(val bottomSheetName: String? = null) : CurrencyEvents()
}