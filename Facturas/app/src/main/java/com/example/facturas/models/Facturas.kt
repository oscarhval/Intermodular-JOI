package com.example.facturas.models

data class Facturas(
    var id: String = "",
    var numeroFactura: String = "",
    var fecha: String = "",
    var emisor: String = "",
    var receptor: String = "",
    var base: String = "",
    var iva: String = "",
    var total: String = "",
    val tipo: String =""

) {

}

