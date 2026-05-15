package com.example.currencyconverterapp.presentation.currency_list.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.example.currencyconverterapp.common.UIConstants
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyConverterState
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Handles the rendering and interaction logic for different bottom sheets based on the current state.
 *
 * @param state The current UI state containing information about which bottom sheet to show and its content.
 * @param coroutineScope The coroutine scope used to launch suspend functions, such as showing or hiding the bottom sheet.
 * @param bottomSheetState The state of the modal bottom sheet used to control its visibility and interaction.
 * @param onEvent Callback to dispatch events related to currency selection, search query updates, and sheet visibility changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandleBottomSheets(
    state: CurrencyConverterState,
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    onEvent: (CurrencyEvents) -> Unit,
) {
    when (state.bottomSheetName) {
        UIConstants.CURRENCY_BOTTOM_SHEET -> {

            CurrencySelectionBottomSheet(
                sheetState = bottomSheetState,
                currencies = state.getFilteredCurrencies(),
                searchQuery = state.searchQuery,
                onCurrencySelected = {
                    onEvent(CurrencyEvents.OnCurrencySelected(it))
                    onEvent(CurrencyEvents.OnSearchQueryChange(""))
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        onEvent(CurrencyEvents.OnBottomSheetVisibilityChange(null))
                        onEvent(CurrencyEvents.OnExpandedChange(false))
                    }
                },
                onSearchQueryChange = {
                    onEvent(CurrencyEvents.OnSearchQueryChange(it))
                },
                onDismiss = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        onEvent(CurrencyEvents.OnBottomSheetVisibilityChange(null))
                        onEvent(CurrencyEvents.OnExpandedChange(false))
                    }
                }
            )
        }
    }
}