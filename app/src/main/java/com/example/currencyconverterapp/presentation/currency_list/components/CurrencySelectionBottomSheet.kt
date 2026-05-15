package com.example.currencyconverterapp.presentation.currency_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A modal bottom sheet that allows users to search and select a currency from a provided list.
 *
 * @param sheetState The state of the bottom sheet used to control its visibility and transitions.
 * @param currencies The list of currencies to display in the bottom sheet.
 * @param searchQuery The current text input used to filter the list of currencies.
 * @param onCurrencySelected Callback triggered when a user selects a currency.
 * @param onSearchQueryChange Callback triggered when the search query changes.
 * @param onDismiss Callback triggered when the bottom sheet is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectionBottomSheet(
    sheetState: SheetState,
    currencies: List<String>,
    searchQuery: String,
    onCurrencySelected: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                placeholder = { Text("Search currency...") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(currencies) { currency ->
                    ListItem(
                        headlineContent = { Text(currency) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(currency) }
                    )
                }
            }
        }
    }
}