package com.example.facturas.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.facturas.R
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize()

    ) {


        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            alignment = Alignment.Center
        )

        Text(
            text = "by Óscar, Jorge & Iván",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    "Email", fontSize = 18.sp
                ) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        TextField(
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    "Contraseña", fontSize = 18.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisibility) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White

            ),
            onClick = {
                if (checkEmpty(email, password)) {
                    login(auth, email, password) { success, msg ->
                        if (success) {
                            navController.navigate("FacturaListView")
                        } else {
                            message = "Error: $msg"
                        }
                    }
                } else {
                    message = "Por favor, completa todos los campos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigate("RegisterScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes cuenta? Regístrate",
                style = TextStyle(color = Color.Black,
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (message.isNotEmpty()) {
            Text(text = message, fontSize = 14.sp)
        }
    }
}



private fun login(auth: FirebaseAuth, email: String, password: String, callback: (Boolean, String?) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, task.exception?.message)
            }
        }
}

private fun checkEmpty(vararg fields: String): Boolean {
    return fields.all { it.isNotEmpty() }
}


