package com.example.currencyconverterapp.presentation.currency_list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.common.Resource
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.use_case.ConvertCurrencyUseCase
import com.example.currencyconverterapp.domain.use_case.GetExchangeRatesUseCase
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyConverterState
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getRatesUseCase: GetExchangeRatesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state

    private var allRates: List<Currency> = emptyList()

    /**
     * Initializes the ViewModel by fetching the exchange rates.
     * This is called when the ViewModel is created.
     */
    init {
        getRates()
    }

    /**
     * Handles the amount change event by updating the state and triggering conversion.
     * @param newAmount The new amount entered by the user.
     */
    private fun onAmountChange(newAmount: String) {
        _state.update { it.copy(amount = newAmount) }
        convert()
    }

    /**
     * Handles the currency selection event by updating the selected currency in the state
     * and triggering conversion.
     * @param currency The currency selected by the user.
     */
    private fun onCurrencySelected(currency: String) {
        _state.update { it.copy(selectedCurrency = currency) }
        convert()
    }

    /**
     * Handles the events related to currency conversion.
     * This function updates the state based on the event type and performs necessary actions.
     * @param event The event to handle, which can be amount change, currency selection,
     *              dropdown expansion change, or search query change.
     */
    fun onEvent(event: CurrencyEvents) {
        when (event) {
            is CurrencyEvents.OnAmountChange -> {
                onAmountChange(event.amount)
            }

            is CurrencyEvents.OnCurrencySelected -> {
                onCurrencySelected(event.currency)
            }

            is CurrencyEvents.OnExpandedChange -> {
                _state.update { it.copy(expanded = event.expanded) }
                if (event.expanded) {
                    // Reset search query when dropdown is expanded
                    _state.update { it.copy(searchQuery = "") }
                }
            }

            is CurrencyEvents.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }

            is CurrencyEvents.OnBottomSheetVisibilityChange -> {
                _state.update { it.copy(bottomSheetName = event.bottomSheetName) }
            }
        }
    }

    /**
     * Fetches the exchange rates from the use case and updates the state accordingly.
     * It collects the result and updates the state with loading, success, or error states.
     */
    private fun getRates() {
        viewModelScope.launch {
            getRatesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }

                    is Resource.Success -> {
                        allRates = result.data
                        _state.update {
                            it.copy(
                                isLoading = false,
                                allCurrencies = allRates.map { currency -> currency.code },
                                error = null
                            )
                        }
                        convert()
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Converts the amount based on the selected currency and updates the state with the conversion result.
     * It retrieves the current state, converts the amount, and updates the rates in the state.
     */
    private fun convert() {
        val currentState = _state.value
        val amount = currentState.amount.toDoubleOrNull() ?: 0.0

        val result = convertCurrencyUseCase.execute(
            amount = amount,
            selectedCurrency = currentState.selectedCurrency,
            rates = allRates
        )

        _state.update { it.copy(rates = result) }
    }
}

