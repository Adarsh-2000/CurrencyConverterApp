package com.example.currencyconverterapp.domain.use_case

import com.example.currencyconverterapp.common.Resource
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetExchangeRatesUseCaseTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetExchangeRatesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        useCase = GetExchangeRatesUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits loading and success when repository returns rates`() = runTest {
        // Given
        val remoteRates = mapOf("USD" to 1.0, "EUR" to 0.85)
        val localRates = listOf(Currency("EUR", 0.85), Currency("USD", 1.0))

        whenever(repository.getRemoteRates()).thenReturn(remoteRates)
        whenever(repository.getLocalRates()).thenReturn(localRates)

        // When
        val emissions = useCase().toList()

        // Then
        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals(localRates.sortedBy { it.code }, (emissions[1] as Resource.Success).data)

        // Verify repository methods
        verify(repository).getRemoteRates()
        verify(repository).saveRates(remoteRates)
        verify(repository).getLocalRates()
    }

    @Test
    fun `invoke emits loading and error when repository throws exception`() = runTest {
        // Given
        whenever(repository.getRemoteRates()).thenThrow(RuntimeException("Network failure"))

        // When
        val emissions = useCase().toList()

        // Then
        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals("Network failure", (emissions[1] as Resource.Error).message)

        // Verify repository methods
        verify(repository).getRemoteRates()
    }
}
