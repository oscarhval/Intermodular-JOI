package com.example.facturas.views

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.facturas.viewmodels.FacturasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaListView(navController: NavController, viewModel: FacturasViewModel) {

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de facturas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black

                ),

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                })
        },


        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("FacturaAddView") },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        FacturasContentView(paddingValues, navController, viewModel)
    }
}



@Composable
fun FacturasContentView(padding: PaddingValues, navController: NavController, viewModel: FacturasViewModel) {
    val state = viewModel.state

    var selectedFilter by remember { mutableStateOf("TODAS") }


    val filteredFacturas = when (selectedFilter) {
        "EMITIDAS" -> state.facturasList.filter { it.tipo == "Compra" }
        "RECIBIDAS" -> state.facturasList.filter { it.tipo == "Venta" }
        else -> state.facturasList
    }

    Column(modifier = Modifier.padding(padding)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selectedFilter = "EMITIDAS" }
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Emitidas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selectedFilter = "TODAS" }
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Gray, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Todas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selectedFilter = "RECIBIDAS" }
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Green, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Recibidas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        LazyColumn {
            items(filteredFacturas) { factura ->
                val borderColor = when (factura.tipo) {
                    "Compra" -> Color.Red
                    "Venta" -> Color.Green
                    else -> Color.Gray
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "N° de Factura: ${factura.numeroFactura}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Fecha: ${factura.fecha}", fontSize = 18.sp)
                            Text(text = "Emisor: ${factura.emisor}", fontSize = 18.sp)
                            Text(text = "Receptor: ${factura.receptor}", fontSize = 18.sp)
                            Text(text = "Base: ${factura.base}€", fontSize = 18.sp)
                            Text(text = "IVA: ${factura.iva}", fontSize = 18.sp)
                            Text(
                                text = "Total: ${factura.total}€",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(
                                        "FacturaUpdateView/" +
                                                "${Uri.encode(factura.id)}/" +
                                                "${Uri.encode(factura.numeroFactura)}/" +
                                                "${Uri.encode(factura.fecha)}/" +
                                                "${Uri.encode(factura.emisor)}/" +
                                                "${Uri.encode(factura.receptor)}/" +
                                                "${Uri.encode(factura.base)}/" +
                                                "${Uri.encode(factura.iva)}/" +
                                                "${Uri.encode(factura.tipo)}"
                                    )
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                onClick = { viewModel.borrarFactura(factura.id) },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
