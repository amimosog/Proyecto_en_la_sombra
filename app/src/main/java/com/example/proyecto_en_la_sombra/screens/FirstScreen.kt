package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

var emailGlobal = ""


@Composable
fun FirstScreen(navController: NavController, context: Context) {
    BodyContent(navController, context = context)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContent(navController: NavController, context: Context) {
    var email by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "Iniciar Sesión")
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "Email",
                    modifier = Modifier.alpha(0.5F),
                    fontSize = 12.sp
                )
            })
        println(email)
        TextField(
            value = pass,
            onValueChange = { pass = it },
            placeholder = {
                Text(
                    text = "Contraseña",
                    modifier = Modifier.alpha(0.5F),
                    fontSize = 12.sp
                )
            }, visualTransformation = PasswordVisualTransformation()
        )
        println(pass)
        println("79")
        val logged = CheckLogin(email = email, pass = pass, context = context)
        println(logged)
        Button(onClick = {
            println("asdaasdada")
            if (logged){
                println("test botton")
                navController.navigate(route = AppScreens.AnimalListScreen.route)
            }else {
                println("try again")
                email = ""
                pass = ""
            }
        }) {
            Text(text = "Iniciar sesión")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CheckLogin(email: String, pass: String, context: Context): Boolean {
    val room: AplicacionDB = AplicacionDB.getInstance(context)
    var checked = false
    var result by remember { mutableStateOf<Cliente?>(null) }
    println("99")
    LaunchedEffect(true) {
        println("101")
        val query =
            GlobalScope.async(Dispatchers.IO) { room.clienteDAO().getClientesByEmail(email) }
        println("104")
        result = query.await()
        print(result)
    }
    if (result != null) {
        println("test")
        if (result!!.email == email && result!!.pass == pass) {
            println("asd")
            checked = true
            emailGlobal = email
        }
    }
    return checked
}