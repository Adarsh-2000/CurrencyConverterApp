package com.example.currencyconverterapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ExchangeRateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: CurrencyRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val rates = repository.getRemoteRates()
            if (rates != null) {
                repository.saveRates(rates)
                Log.d("ExchangeRateWorker", "Rates updated via WorkManager")
                Result.success()
            } else {
                Log.w("ExchangeRateWorker", "Fetch failed, retrying")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e("ExchangeRateWorker", "Worker error", e)
            Result.failure()
        }
    }
}




