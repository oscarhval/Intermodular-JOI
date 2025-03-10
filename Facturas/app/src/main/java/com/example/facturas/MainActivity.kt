package com.example.facturas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.facturas.navigation.AppNavigation
import com.example.facturas.firebase.FacturasDatabase
import com.example.facturas.ui.theme.FacturasTheme
import com.example.facturas.viewmodels.FacturasViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FacturasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository = FacturasDatabase()
                    val viewModel = FacturasViewModel(repository)

                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

