package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.navigation.AppScreens

@Composable
fun FirstScreen(navController: NavController){
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Hola navegacion")
        Button(onClick = {
            navController.navigate(route = AppScreens.ListScreen.route)
        }){
            Text(text = "Navega")
        }
    }
}