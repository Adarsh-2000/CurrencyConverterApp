package com.example.currencyconverterapp.currency_list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.currencyconverterapp.common.UIConstants
import com.example.currencyconverterapp.presentation.currency_list.CurrencyConverterScreen
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyConverterState
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyEvents
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CurrencyConverterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var state: CurrencyConverterState
    private lateinit var events: MutableList<CurrencyEvents>

    @Before
    fun setUp() {
        // Dummy initial state
        state = CurrencyConverterState(
            amount = "",
            selectedCurrency = "USD",
            allCurrencies = listOf("USD", "EUR", "INR"),
            rates = listOf("INR" to 83.00, "EUR" to 0.85),
            isLoading = false,
            error = null,
            expanded = true,
            bottomSheetName = UIConstants.CURRENCY_BOTTOM_SHEET,
            searchQuery = ""
        )
        events = mutableListOf()
    }

    @Test
    fun amountInput_showsError_onInvalidAmount() {
        state = state.copy(amount = "0")

        composeTestRule.setContent {
            CurrencyConverterScreen(
                state = state,
                onEvent = { events.add(it) }
            )
        }

        composeTestRule
            .onNodeWithText("Enter a valid amount greater than 0")
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displays_and_selects_currency() {
        state = state.copy(
            bottomSheetName = UIConstants.CURRENCY_BOTTOM_SHEET,
            searchQuery = "",
            expanded = true
        )

        composeTestRule.setContent {
            CurrencyConverterScreen(
                state = state,
                onEvent = { events.add(it) }
            )
        }

        // Assert the bottom sheet search field is visible
        composeTestRule
            .onNodeWithText("Search currency...")
            .assertIsDisplayed()

        // Perform click on INR currency
        composeTestRule
            .onNodeWithText("INR")
            .performClick()

        // Assert currency selection event is triggered
        assert(events.contains(CurrencyEvents.OnCurrencySelected("INR")))
    }

    @Test
    fun bottomSheet_filtersCurrencyList() {
        state = state.copy(
            bottomSheetName = UIConstants.CURRENCY_BOTTOM_SHEET,
            searchQuery = "EU",
            expanded = true
        )

        composeTestRule.setContent {
            CurrencyConverterScreen(
                state = state,
                onEvent = { events.add(it) }
            )
        }

        // Should show EUR
        composeTestRule
            .onNodeWithText("EUR")
            .assertIsDisplayed()

        // Should not show INR
        composeTestRule
            .onNodeWithText("INR")
            .assertDoesNotExist()
    }

    @Test
    fun currencyGrid_displaysExchangeRates() {
        composeTestRule.setContent {
            CurrencyConverterScreen(
                state = state,
                onEvent = { events.add(it) }
            )
        }

        composeTestRule.onNodeWithText("INR\n83.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("EUR\n0.85").assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessage() {
        val errorState = state.copy(error = "Network Error")

        composeTestRule.setContent {
            CurrencyConverterScreen(
                state = errorState,
                onEvent = { events.add(it) }
            )
        }

        composeTestRule.onNodeWithText("Network Error").assertIsDisplayed()
    }
}
