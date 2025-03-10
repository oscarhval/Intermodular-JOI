package com.example.facturas.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.facturas.models.Facturas
import com.example.facturas.viewmodels.FacturasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaAddView(navController: NavController, viewModel: FacturasViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Agregar factura",
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp()}
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        FacturaAddView(paddingValues, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaAddView(it: PaddingValues, navController: NavController, viewModel: FacturasViewModel) {
    var numeroFactura by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var emisor by remember { mutableStateOf("") }
    var receptor by remember { mutableStateOf("") }
    var base by remember { mutableStateOf("") }

    var expandedTipo by remember { mutableStateOf(false) }
    var selectedTipo by remember { mutableStateOf("Seleccionar tipo de factura") }

    var expandedIva by remember { mutableStateOf(false) }
    var selectedIva by remember { mutableStateOf("Seleccionar IVA") }
    val ivaOptions = listOf("21%", "10%", "4%")

    val baseValue = base.toDoubleOrNull() ?: 0.0
    val ivaRate = selectedIva.replace("%", "").toDoubleOrNull()?.div(100) ?: 0.0
    val calculatedIva = baseValue * ivaRate
    val calculatedTotal = baseValue + calculatedIva

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        OutlinedTextField(
            value = numeroFactura,
            onValueChange = { numeroFactura = it },
            label = { Text(text = "Nº de factura") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text(text = "Fecha de emisión") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        )

        OutlinedTextField(
            value = emisor,
            onValueChange = { emisor = it },
            label = { Text(text = "Datos del emisor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        )

        OutlinedTextField(
            value = receptor,
            onValueChange = { receptor = it },
            label = { Text(text = "Datos del receptor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        )

        OutlinedTextField(
            value = base,
            onValueChange = { base = it },
            label = { Text(text = "Base imponible (€)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)

        )

        Box(modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { expandedTipo = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = selectedTipo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            DropdownMenu(
                expanded = expandedTipo,
                onDismissRequest = { expandedTipo = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Compra") },
                    onClick = {
                        selectedTipo = "Compra"
                        expandedTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Venta") },
                    onClick = {
                        selectedTipo = "Venta"
                        expandedTipo = false
                    }
                )
            }
        }

        Box(modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { expandedIva = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = selectedIva, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            DropdownMenu(
                expanded = expandedIva,
                onDismissRequest = { expandedIva = false }
            ) {
                ivaOptions.forEach { ivaOption ->
                    DropdownMenuItem(
                        text = { Text(ivaOption) },
                        onClick = {
                            selectedIva = ivaOption
                            expandedIva = false
                        }
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 16.dp)
                .padding(bottom = 10.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = "Total: %.2f".format(calculatedTotal)+ "€")
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            onClick = {
                val factura = Facturas(
                    numeroFactura = numeroFactura,
                    fecha = fecha,
                    emisor = emisor,
                    receptor = receptor,
                    base = base,
                    iva = selectedIva,
                    total = calculatedTotal.toString(),
                    tipo = selectedTipo
                )

                viewModel.agregarFactura(factura)
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 16.dp),

        ) {
            Text(text = "Agregar", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        }
    }
}
