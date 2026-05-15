package com.example.currencyconverterapp.presentation.currency_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.currencyconverterapp.utils.ValidationUtils.filterNumericInput

/**
 * The amount input field.
 * @param amount The current amount.
 * @param onAmountChange The callback to be invoked when the amount is changed.
 * @param isError The current state of the error.
 */

@Composable
fun AmountInputField(
    amount: String,
    onAmountChange: (String) -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        value = amount,
        onValueChange = { newValue ->
            if (filterNumericInput(newValue) == null) {
                // if input is invalid, do not update the amount
                return@OutlinedTextField
            } else {
                // Update the amount with valid input
                onAmountChange(newValue)
            }
        },
        label = { Text("Amount") },
        placeholder = { Text("Please Enter Amount") },
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )

    if (isError) {
        Text(
            text = "Enter a valid amount greater than 0",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
