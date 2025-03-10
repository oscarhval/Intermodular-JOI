package com.example.facturas.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@Composable
fun RegisterScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(top = 8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.Black
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
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

                )
            },
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
                val icon =
                    if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisibility) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }


        )

        TextField(
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repetir contraseña", fontSize = 18.sp) },
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
                if (password == repeatPassword && checkEmpty(email, password, repeatPassword)) {
                    register(auth, email, password) { success, msg ->
                        message = if (success) "Te has registrado." else "Error: $msg"
                    }
                } else {
                    message = "Las contraseñas no coinciden o hay campos vacíos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigate("LoginScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                style = TextStyle(
                    color = Color.Black,
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


private fun register(auth: FirebaseAuth, email: String, password: String, callback: (Boolean, String?) -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
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