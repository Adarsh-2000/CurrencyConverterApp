package com.example.currencyconverterapp.presentation.currency_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.common.UIConstants
import com.example.currencyconverterapp.presentation.currency_list.components.AmountInputField
import com.example.currencyconverterapp.presentation.currency_list.components.CurrencyContent
import com.example.currencyconverterapp.presentation.currency_list.components.CurrencySelectorDropdown
import com.example.currencyconverterapp.presentation.currency_list.components.HandleBottomSheets
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyConverterState
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyEvents
import com.example.currencyconverterapp.utils.ValidationUtils.isInvalidAmount
import kotlinx.coroutines.launch

/**
 * Screen for displaying the CurrencyConverter.
 * @param state The current state of the CurrencyConverter.
 * @param onEvent The callback to be invoked when an event is triggered.
 * @param modifier The modifier to be applied to the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(
    modifier: Modifier = Modifier,
    state: CurrencyConverterState,
    onEvent: (CurrencyEvents) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    // Handles the bottom sheet visibility and state changes
    HandleBottomSheets(
        state = state,
        coroutineScope = coroutineScope,
        bottomSheetState = bottomSheetState,
        onEvent = onEvent
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input field for entering the amount to convert
        AmountInputField(
            amount = state.amount,
            onAmountChange = {
                // Trigger the event when the amount changes
                onEvent(CurrencyEvents.OnAmountChange(it))
            },
            isError = isInvalidAmount(state.amount)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Currency dropdown for selecting the base currency
        CurrencySelectorDropdown(
            expanded = state.expanded,
            onExpandedChange = {
                // Toggle the dropdown expansion state and show the bottom sheet
                onEvent(CurrencyEvents.OnExpandedChange(!state.expanded))
                if (!state.expanded) {
                    coroutineScope.launch {
                        onEvent(CurrencyEvents.OnBottomSheetVisibilityChange(UIConstants.CURRENCY_BOTTOM_SHEET))
                        bottomSheetState.show()
                    }
                }
            },
            selectedCurrency = state.selectedCurrency
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Renders the main UI based on the current state
        CurrencyContent(state)
    }
}