package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.navigation.NavController

import com.example.proyecto_en_la_sombra.navigation.AppScreens


@Composable
fun FirstScreen(navController: NavController) {
    BodyContent(navController)
}


@Composable
fun BodyContent(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola navegacion")
            Button(onClick = {
                navController.navigate(route = AppScreens.AnimalListScreen.route)
            }) {
                Text(text = "Navega")
            }
        }
}
