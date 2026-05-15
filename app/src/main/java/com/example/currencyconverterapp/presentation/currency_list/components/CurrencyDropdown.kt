package com.example.currencyconverterapp.presentation.currency_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Dropdown menu for selecting the currency.
 *
 * @param expanded Indicates whether the dropdown menu is expanded.
 * @param onExpandedChange Callback to be invoked when the dropdown menu's expanded state changes.
 * @param selectedCurrency The currently selected currency to be displayed in the text field.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectorDropdown(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    selectedCurrency: String
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Readonly OutlinedTextField for selected currency
        OutlinedTextField(
            readOnly = true,
            value = selectedCurrency,
            onValueChange = {},
            label = { Text("Currency") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
    }
}
