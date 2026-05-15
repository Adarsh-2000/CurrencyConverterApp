package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.local.CurrencyDao
import com.example.currencyconverterapp.data.local.CurrencyEntity
import com.example.currencyconverterapp.data.remote.CurrencyApi
import com.example.currencyconverterapp.data.remote.dto.CurrencyRatesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryImplTest {

    private lateinit var repository: CurrencyRepositoryImpl
    private lateinit var api: CurrencyApi
    private lateinit var dao: CurrencyDao

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        dao = mockk(relaxUnitFun = true)
        repository = CurrencyRepositoryImpl(api, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRemoteRates returns rates when API call succeeds`() = runTest {
        val rates = mapOf("USD" to 1.0, "INR" to 83.0)
        coEvery { api.getLatestRates(any()) } returns CurrencyRatesDto(rates = rates)

        val result = repository.getRemoteRates()

        assertEquals(rates, result)
        coVerify { api.getLatestRates(any()) }
    }

    @Test
    fun `getRemoteRates returns null when API call fails`() = runTest {
        coEvery { api.getLatestRates(any()) } throws IOException("Network error")

        val result = repository.getRemoteRates()

        assertNull(result)
        coVerify { api.getLatestRates(any()) }
    }

    @Test
    fun `getLocalRates returns mapped currencies from DB`() = runTest {
        val entities = listOf(
            CurrencyEntity("USD", 1.0),
            CurrencyEntity("INR", 83.0)
        )
        coEvery { dao.getAllRates() } returns entities

        val result = repository.getLocalRates()

        assertEquals(2, result.size)
        assertEquals("USD", result[0].code)
        assertEquals(83.0, result[1].rateAgainstBase, 0.001)
        coVerify { dao.getAllRates() }
    }

    @Test
    fun `saveRates clears and inserts new rates`() = runTest {
        val rates = mapOf("USD" to 1.0, "EUR" to 0.9)

        repository.saveRates(rates)

        val expectedEntities = rates.map { CurrencyEntity(it.key, it.value) }

        coVerifySequence {
            dao.clearRates()
            dao.insertAll(expectedEntities)
        }
    }

    @Test
    fun `clearRates calls DAO clearRates`() = runTest {
        repository.clearRates()
        coVerify { dao.clearRates() }
    }
}
