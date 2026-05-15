package com.example.currencyconverterapp.domain.use_case

import com.example.currencyconverterapp.domain.model.Currency
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConvertCurrencyUseCaseTest {

    private lateinit var useCase: ConvertCurrencyUseCase

    @Before
    fun setup() {
        useCase = ConvertCurrencyUseCase()
    }

    @Test
    fun `returns empty list if selected currency not in rates`() {
        val rates = listOf(
            Currency("USD", 1.0),
            Currency("EUR", 0.9)
        )

        val result = useCase.execute(
            amount = 100.0,
            selectedCurrency = "INR",
            rates = rates
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `converts correctly when selected currency is USD`() {
        val rates = listOf(
            Currency("USD", 1.0),
            Currency("EUR", 0.85),
            Currency("INR", 83.0)
        )

        val result = useCase.execute(
            amount = 100.0,
            selectedCurrency = "USD",
            rates = rates
        ).toMap()

        assertEquals(100.0, result["USD"] ?: 0.0, 0.01)
        assertEquals(85.0, result["EUR"] ?: 0.0, 0.01)
        assertEquals(8300.0, result["INR"] ?: 0.0, 0.01)
    }

    @Test
    fun `converts correctly when selected currency is INR`() {
        val rates = listOf(
            Currency("USD", 1.0),
            Currency("EUR", 0.85),
            Currency("INR", 83.0)
        )

        val result = useCase.execute(
            amount = 8300.0,
            selectedCurrency = "INR",
            rates = rates
        ).toMap()

        assertEquals(100.0, result["USD"] ?: 0.0, 0.01)
        assertEquals(85.0, result["EUR"] ?: 0.0, 0.01)
        assertEquals(8300.0, result["INR"] ?: 0.0, 0.01)
    }

    @Test
    fun `handles zero amount correctly`() {
        val rates = listOf(
            Currency("USD", 1.0),
            Currency("EUR", 0.85),
            Currency("INR", 83.0)
        )

        val result = useCase.execute(
            amount = 0.0,
            selectedCurrency = "USD",
            rates = rates
        )

        assertTrue(result.all { it.second == 0.0 })
    }

    @Test
    fun `rounds converted amount to 2 decimal places`() {
        val rates = listOf(
            Currency("USD", 1.0),
            Currency("JPY", 143.1285)
        )

        val result = useCase.execute(
            amount = 100.0,
            selectedCurrency = "USD",
            rates = rates
        ).toMap()

        assertEquals(14312.85, result["JPY"] ?: 0.0, 0.01)
    }
}