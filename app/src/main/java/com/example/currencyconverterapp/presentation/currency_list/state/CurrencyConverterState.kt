package com.example.currencyconverterapp.presentation.currency_list.state

data class CurrencyConverterState(
    val isLoading: Boolean = false,
    val rates: List<Pair<String, Double>> = emptyList(),
    val error: String? = null,
    val amount: String = "1",
    val selectedCurrency: String = "USD",
    val allCurrencies: List<String> = emptyList(),
    val searchQuery: String = "",
    var expanded: Boolean = false,
    val bottomSheetName: String? = null
) {

    /**
     * Returns a list of currencies filtered by the current search query.
     * The result is sorted alphabetically and includes only those currencies
     * that contain the `searchQuery` substring, case-insensitively.
     */
    fun getFilteredCurrencies(): List<String> {
        return allCurrencies.sorted().filter { it.contains(searchQuery, ignoreCase = true) }
    }
}