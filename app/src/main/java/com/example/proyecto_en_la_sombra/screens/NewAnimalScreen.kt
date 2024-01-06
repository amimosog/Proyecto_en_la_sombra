package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.Model.Animal
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.Repository.animalRepository
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun newAnimalComponents(navController: NavController, animalRepository: animalRepository, idOrg: Long) {
    var nombre: String by remember { mutableStateOf("") }
    var desc: String by remember { mutableStateOf("") }
    var fotos: String by remember { mutableStateOf("") }

    Box (
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_register),
                contentScale = ContentScale.FillBounds
            ), contentAlignment = Alignment.TopCenter
    ){
        Image(
            painter = painterResource(R.drawable.background_register),
            contentDescription = "add_animal_background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.80F)
                .fillMaxWidth(0.85F)
                .padding(top = 30.dp)
                .offset(y = 30.dp)
                .background(Color.White, RoundedCornerShape(8.dp))

        ) {
            item {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Añadir Animal",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.71F)
                )
                Spacer(Modifier.height(40.dp))
                TextField(
                    shape = RoundedCornerShape(8.dp),
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = { Text(text = "Nombre", modifier = Modifier.alpha(0.5F)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    shape = RoundedCornerShape(8.dp),
                    value = desc,
                    onValueChange = { desc = it },
                    placeholder = { Text(text = "Descripcion", modifier = Modifier.alpha(0.5F)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    shape = RoundedCornerShape(8.dp),
                    value = fotos,
                    onValueChange = { fotos = it },
                    placeholder = { Text(text = "Fotos (dirección imagenes)", modifier = Modifier.alpha(0.5F)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(35.dp))
                Button(onClick = {
                    if (nombre.isNotEmpty()){
                        GlobalScope.launch {
                            var newAnimal = Animal(nombre, desc, idOrg, fotos)

                            animalRepository.insertAnimal(newAnimal)
                            Log.i("Crear animal", "Animal creado")
                        }

                        navController.popBackStack()
                    }
                }) {
                    Text(text = "Añadir Animal")
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}