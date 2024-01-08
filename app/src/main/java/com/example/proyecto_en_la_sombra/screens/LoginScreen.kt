package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.Repository.clientRepository
import com.example.proyecto_en_la_sombra.emailActual
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun LoginActivity(navController: NavController, users: clientRepository) {
    LoginComponents(navController, users)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponents(navController: NavController, users: clientRepository) {

    var email: String by remember { mutableStateOf("") }
    var pass: String by remember { mutableStateOf("") }
    var passCheck: String by remember { mutableStateOf("") } //En este contexto almacenara la contrasena almacenada en la base de datos
    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.background_register),
            contentDescription = "register_background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.7F)
                .fillMaxWidth(0.85F)
                .padding(top = 30.dp)
                .offset(y = 50.dp)
                .background(Color.White, RoundedCornerShape(8.dp))

        ) {
            item {
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "Iniciar Sesión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.71F)
                )
                Spacer(Modifier.height(60.dp))
                TextField(
                    value = email,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { email = it },
                    placeholder = { Text(text = "Email", modifier = Modifier.alpha(0.5F)) },
                    trailingIcon = {
                        Icon(Outlined.Email, contentDescription = "email_icon")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = pass,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { pass = it },
                    placeholder = { Text(text = "Contraseña", modifier = Modifier.alpha(0.5F)) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            if (passwordVisible) {
                                Icon(Outlined.Visibility, contentDescription = "pass_visibility_on")
                            } else Icon(
                                Outlined.VisibilityOff,
                                contentDescription = "pass_visibility_off"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )

                Spacer(Modifier.height(35.dp))
                Button(onClick = {
                    //Comprobar que la contrasena es la correcta para ese usuario
                    var cliente : Cliente
                    var clienteExistente : Boolean = false
                    if (email.isNotEmpty() && pass.isNotEmpty()) {
                        GlobalScope.launch {
                            try {
                                cliente = users.getClienteByEmail(email)
                                clienteExistente = true
                                passCheck = cliente.password
                                Log.i("Id usuario inicia", cliente.idCliente.toString())
                            } catch (e: Exception) {
                                Log.i("obtencion usuario por email", "no existe")
                            }
                        }
                        if (passCheck.equals(pass)) {
                            emailActual = email
                            //Navegamos a la pantalla de lista de animales
                            navController.navigate(route = AppScreens.AnimalListScreen.route)
                        } else {
                            //mostrar mensaje de error
                        }
                    }
                }) {
                    Text(text = "Iniciar Sesión")
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
