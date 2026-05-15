package com.example.currencyconverterapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverterapp.presentation.currency_list.CurrencyConverterScreen
import com.example.currencyconverterapp.presentation.currency_list.viewModel.CurrencyConverterViewModel
import com.example.currencyconverterapp.presentation.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.worker.scheduleRateSync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        scheduleRateSync(applicationContext)
        setContent {
            CurrencyConverterAppTheme {
                val viewModel: CurrencyConverterViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onEvent = viewModel::onEvent,
                    )
                }
            }
        }
    }
}
