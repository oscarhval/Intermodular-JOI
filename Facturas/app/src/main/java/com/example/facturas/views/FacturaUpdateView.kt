package com.example.facturas.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
fun FacturaUpdateView(navController: NavController, viewModel: FacturasViewModel, id: String, numeroFactura: String?, fecha: String?, emisor: String?, receptor: String?, base: String?, ivaInitial: String?, tipoInitial: String?
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Actualizar factura",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black),

                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
        UpdateContentView(
            padding = paddingValues,
            navController = navController,
            viewModel = viewModel,
            id = id,
            numeroFactura = numeroFactura,
            fecha = fecha,
            emisor = emisor,
            receptor = receptor, base = base, ivaInitial = ivaInitial, tipoInitial = tipoInitial
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateContentView(padding: PaddingValues, navController: NavController, viewModel: FacturasViewModel, id: String, numeroFactura: String?, fecha: String?, emisor: String?, receptor: String?, base: String?, ivaInitial: String?, tipoInitial: String?
) {
    var numeroFacturaState by remember { mutableStateOf(numeroFactura ?: "") }
    var fechaState by remember { mutableStateOf(fecha ?: "") }
    var emisorState by remember { mutableStateOf(emisor ?: "") }
    var receptorState by remember { mutableStateOf(receptor ?: "") }
    var baseState by remember { mutableStateOf(base ?: "") }

    var expandedTipo by remember { mutableStateOf(false) }
    var selectedTipo by remember { mutableStateOf(if (!tipoInitial.isNullOrBlank()) tipoInitial!! else "Seleccionar tipo de factura") }
    val tipoOptions = listOf("Compra", "Venta")


    var expandedIva by remember { mutableStateOf(false) }
    var selectedIva by remember { mutableStateOf(if (!ivaInitial.isNullOrBlank()) ivaInitial!! else "Seleccionar IVA") }
    val ivaOptions = listOf("21%", "10%", "4%")

    val baseValue = baseState.toDoubleOrNull() ?: 0.0
    val ivaRate = selectedIva.replace("%", "").toDoubleOrNull()?.div(100) ?: 0.0
    val calculatedTotal = baseValue + (baseValue * ivaRate)
    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = numeroFacturaState,
            onValueChange = { numeroFacturaState = it },
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
            value = fechaState,
            onValueChange = { fechaState = it },
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
            value = emisorState,
            onValueChange = { emisorState = it },
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
            value = receptorState,
            onValueChange = { receptorState = it },
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
            value = baseState,
            onValueChange = { baseState = it },
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Button(
                onClick = { expandedTipo = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedTipo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            DropdownMenu(
                expanded = expandedTipo,
                onDismissRequest = { expandedTipo = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                tipoOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedTipo = option
                            expandedTipo = false
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Button(
                onClick = { expandedIva = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedIva, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            DropdownMenu(
                expanded = expandedIva,
                onDismissRequest = { expandedIva = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                ivaOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedIva = option
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
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = "Total: %.2f".format(calculatedTotal)+ "€", fontWeight = FontWeight.Bold)
            }
        }
        Button(
            onClick = {
                val factura = Facturas(
                    id = id,
                    numeroFactura = numeroFacturaState,
                    fecha = fechaState,
                    emisor = emisorState,
                    receptor = receptorState,
                    base = baseState,
                    iva = selectedIva,
                    total = calculatedTotal.toString(),
                    tipo = selectedTipo
                )
                viewModel.actualizarFactura(factura)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(text = "Guardar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
