package com.example.facturas.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facturas.firebase.FacturasDatabase
import com.example.facturas.models.Facturas
import com.example.facturas.states.FacturasState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FacturasViewModel(
    private val repository: FacturasDatabase
) : ViewModel() {

    var state by mutableStateOf(FacturasState())
        private set

    init {
        obtenerFacturas()
    }
    private fun obtenerFacturas() {
        viewModelScope.launch {
            val facturasList = repository.obtenerFacturas()
            state = state.copy(facturasList = facturasList)
//          repository.obtenerFacturas().collectLatest { facturasList ->
//              state = state.copy(facturasList = facturasList)
//          }
        }
    }


    fun agregarFactura(factura: Facturas) = viewModelScope.launch {
        repository.agregarFactura(factura)
        obtenerFacturas()
    }

    fun actualizarFactura(factura: Facturas) = viewModelScope.launch {
        repository.actualizarFactura(factura)
        obtenerFacturas()
    }

    fun borrarFactura(id: String) = viewModelScope.launch {
        repository.eliminarFactura(id)
        obtenerFacturas()
    }
}
