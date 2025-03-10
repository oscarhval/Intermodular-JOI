package com.example.facturas.firebase

import com.example.facturas.models.Facturas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FacturasDatabase {

    private val db = FirebaseFirestore.getInstance()
    private val facturasCollection = db.collection("facturas")

    suspend fun agregarFactura(factura: Facturas) {
        val facturaData = hashMapOf(
            "numeroFactura" to factura.numeroFactura,
            "fecha" to factura.fecha,
            "emisor" to factura.emisor,
            "receptor" to factura.receptor,
            "base" to factura.base,
            "iva" to factura.iva,
            "total" to factura.total,
            "tipo" to factura.tipo
        )
        facturasCollection.add(facturaData).await()
    }

    suspend fun obtenerFacturas(): List<Facturas> {
        return try {
            val snapshot = facturasCollection.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Facturas::class.java)?.copy(
                    id = doc.id,
                    tipo = doc.getString("tipo") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun actualizarFactura(factura: Facturas) {
        val facturaData = hashMapOf(
            "numeroFactura" to factura.numeroFactura,
            "fecha" to factura.fecha,
            "emisor" to factura.emisor,
            "receptor" to factura.receptor,
            "base" to factura.base,
            "iva" to factura.iva,
            "total" to factura.total,
            "tipo" to factura.tipo
        )
        factura.id.let { id ->
            facturasCollection.document(id).set(facturaData).await()
        }
    }

    suspend fun eliminarFactura(id: String) {
        facturasCollection.document(id).delete().await()
    }
}
