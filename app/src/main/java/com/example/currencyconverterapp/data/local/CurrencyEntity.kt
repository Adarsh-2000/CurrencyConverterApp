package com.example.currencyconverterapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyEntity(
    @PrimaryKey val currency: String,
    val rateAgainstBase: Double
)