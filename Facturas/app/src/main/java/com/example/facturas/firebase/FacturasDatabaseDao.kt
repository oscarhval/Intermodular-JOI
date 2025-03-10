package com.example.facturas.firebase

import com.example.facturas.models.Facturas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FacturasDatabaseDao {

    private val db = FirebaseFirestore.getInstance()
    private val facturasCollection = db.collection("facturas")

    fun obtenerFacturas(): Flow<List<Facturas>> = flow {
        try {
            val snapshot = facturasCollection.get().await()
            val facturas = snapshot.documents.mapNotNull { it.toObject(Facturas::class.java) }
            emit(facturas)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun obtenerFactura(id: String): Flow<Facturas?> = flow {
        try {
            val document = facturasCollection.document(id).get().await()
            emit(document.toObject(Facturas::class.java))
        } catch (e: Exception) {
            emit(null)
        }
    }

    suspend fun agregarFactura(factura: Facturas) {
        val docRef = facturasCollection.document()
        factura.id = docRef.id
        docRef.set(factura).await()
    }

    suspend fun actualizarFactura(factura: Facturas) {
        if (factura.id.isNotEmpty()) {
            facturasCollection.document(factura.id).set(factura).await()
        }
    }

    suspend fun borrarFactura(id: String) {
        facturasCollection.document(id).delete().await()
    }
}

