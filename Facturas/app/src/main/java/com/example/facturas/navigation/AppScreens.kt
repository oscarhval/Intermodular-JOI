package com.example.facturas.navigation

sealed class AppScreens(val ruta: String) {

    object LoginScreen : AppScreens("LoginScreen")
    object RegisterScreen : AppScreens("RegisterScreen")
    object CompraOVentas : AppScreens("CompraOVentas")
    object FacturaListView : AppScreens("FacturaListView")
    object FacturaAddView : AppScreens("FacturaAddView")

    object FacturaUpdateView : AppScreens("FacturaUpdateView/{id}/{numeroFactura}/{fecha}/{emisor}/{receptor}/{base}/{iva}/{tipo}") {
        fun createRoute(
            id: String,
            numeroFactura: String,
            fecha: String,
            emisor: String,
            receptor: String,
            base: String,
            iva: String,
            tipo: String
        ): String {
            return "FacturaUpdateView/$id/$numeroFactura/$fecha/$emisor/$receptor/$base/$iva/$tipo"
        }
    }
}
