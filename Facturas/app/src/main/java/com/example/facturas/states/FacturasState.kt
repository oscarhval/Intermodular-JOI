package com.example.facturas.states

import com.example.facturas.models.Facturas

data class FacturasState(
    val facturasList: List<Facturas> = emptyList()
)
