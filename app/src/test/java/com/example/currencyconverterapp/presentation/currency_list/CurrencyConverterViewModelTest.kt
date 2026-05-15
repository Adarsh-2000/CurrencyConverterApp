package com.example.currencyconverterapp.presentation.currency_list

import com.example.currencyconverterapp.common.Resource
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.use_case.ConvertCurrencyUseCase
import com.example.currencyconverterapp.domain.use_case.GetExchangeRatesUseCase
import com.example.currencyconverterapp.presentation.currency_list.state.CurrencyEvents
import com.example.currencyconverterapp.presentation.currency_list.viewModel.CurrencyConverterViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyConverterViewModelTest {

    private lateinit var getRatesUseCase: GetExchangeRatesUseCase
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
    private lateinit var viewModel: CurrencyConverterViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getRatesUseCase = mock()
        convertCurrencyUseCase = mock()

        // default to empty success for init
        runTest {
            whenever(getRatesUseCase()).thenReturn(
                flowOf(Resource.Success(emptyList()))
            )
        }

        viewModel = CurrencyConverterViewModel(getRatesUseCase, convertCurrencyUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRates emits loading and success, updates currencies`() = runTest {
        val fakeRates = listOf(
            Currency("USD", 1.0),
            Currency("EUR", 0.85)
        )

        whenever(getRatesUseCase()).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(fakeRates)
            )
        )

        viewModel = CurrencyConverterViewModel(getRatesUseCase, convertCurrencyUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(false, state.isLoading)
        assertEquals(listOf("USD", "EUR"), state.allCurrencies)
        assertNull(state.error)
    }

    @Test
    fun `onAmountChange triggers conversion`() = runTest {
        val fakeRates = listOf(Currency("USD", 1.0), Currency("INR", 83.0))
        val convertedRates = listOf("USD" to 100.0, "INR" to 8300.0)

        whenever(getRatesUseCase()).thenReturn(flowOf(Resource.Success(fakeRates)))
        whenever(
            convertCurrencyUseCase.execute(
                amount = 100.0,
                selectedCurrency = "USD",
                rates = fakeRates
            )
        ).thenReturn(convertedRates)

        viewModel = CurrencyConverterViewModel(getRatesUseCase, convertCurrencyUseCase)

        advanceUntilIdle()

        viewModel.onEvent(CurrencyEvents.OnCurrencySelected("USD"))
        viewModel.onEvent(CurrencyEvents.OnAmountChange("100"))

        val state = viewModel.state.value

        assertEquals("100", state.amount)
        assertEquals("USD", state.selectedCurrency)
        assertEquals(convertedRates, state.rates)
    }

    @Test
    fun `onCurrencySelected triggers conversion`() = runTest {
        val fakeRates = listOf(Currency("USD", 1.0), Currency("EUR", 0.9))
        val converted = listOf("USD" to 100.0, "EUR" to 90.0)

        whenever(getRatesUseCase()).thenReturn(flowOf(Resource.Success(fakeRates)))
        whenever(
            convertCurrencyUseCase.execute(100.0, "USD", fakeRates)
        ).thenReturn(converted)

        viewModel = CurrencyConverterViewModel(getRatesUseCase, convertCurrencyUseCase)

        advanceUntilIdle()

        viewModel.onEvent(CurrencyEvents.OnCurrencySelected("USD"))
        viewModel.onEvent(CurrencyEvents.OnAmountChange("100"))

        val state = viewModel.state.value
        assertEquals("USD", state.selectedCurrency)
        assertEquals(converted, state.rates)
    }

    @Test
    fun `onExpandedChange updates dropdown state`() = runTest {
        viewModel.onEvent(CurrencyEvents.OnExpandedChange(true))
        val state = viewModel.state.value
        assertTrue(state.expanded)
    }

    @Test
    fun `onSearchQueryChange updates query`() = runTest {
        viewModel.onEvent(CurrencyEvents.OnSearchQueryChange("inr"))
        val state = viewModel.state.value
        assertEquals("inr", state.searchQuery)
    }

    @Test
    fun `getRates emits error updates error in state`() = runTest {
        whenever(getRatesUseCase()).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Error("Failed to load")
            )
        )

        viewModel = CurrencyConverterViewModel(getRatesUseCase, convertCurrencyUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Failed to load", state.error)
    }
}