package com.example.currencyconverterapp.domain.use_case

import com.example.currencyconverterapp.common.Resource
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<Resource<List<Currency>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteRates = repository.getRemoteRates()
            remoteRates?.let {
                repository.saveRates(it)
            }

            val localRates = repository.getLocalRates().sortedBy { it.code }
            emit(Resource.Success(localRates))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}



