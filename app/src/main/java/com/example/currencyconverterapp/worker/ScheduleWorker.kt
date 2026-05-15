package com.example.currencyconverterapp.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyconverterapp.common.WorkManagerConstants
import java.util.concurrent.TimeUnit

fun scheduleRateSync(context: Context) {
    val request = PeriodicWorkRequestBuilder<ExchangeRateWorker>(30, TimeUnit.MINUTES)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WorkManagerConstants.WORK_NAME_EXCHANGE_RATE_SYNC,
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}
