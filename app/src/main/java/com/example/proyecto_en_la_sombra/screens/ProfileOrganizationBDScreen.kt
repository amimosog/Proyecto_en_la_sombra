package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Animal
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.Model.Donacion
import com.example.proyecto_en_la_sombra.Model.Protectora
import com.example.proyecto_en_la_sombra.Model.Valoracion
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.Repository.animalRepository
import com.example.proyecto_en_la_sombra.Repository.clientRepository
import com.example.proyecto_en_la_sombra.Repository.donacionRepository
import com.example.proyecto_en_la_sombra.Repository.protectoraRepository
import com.example.proyecto_en_la_sombra.Repository.valoracionRepository
import com.example.proyecto_en_la_sombra.emailActual
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun profileOrganizationBD(
    navController: NavController,
    id: Long,
    context: Context,
    users: clientRepository,
    protectoraRepository: protectoraRepository,
    valoracionRepository: valoracionRepository,
    donacionRepository: donacionRepository,
    animals: animalRepository
) {
    var reviews by remember { mutableStateOf<List<Valoracion>?>(null) }

    var org: Protectora
    runBlocking {
        org = protectoraRepository.getOrganizacionId(id)
    }
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_register),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.90F)
                .fillMaxHeight(0.95F)
                .offset(y = 12.dp)
                .background(Color.White, RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row {
                    if (org.logo != null)
                        OrgImageBD(org.logo!!)
                    else
                        OrgImageBD("https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U")

                    OrgInfoBD(org)
                }

                DetailInfoBD(navController, org, context, users, donacionRepository)

                OrgGalleryBD(org.idProtectora.toString(), navController, animals)
                ReviewsFieldBD(id.toString(), users, valoracionRepository)

                //Se llama a pintar los comentarios
                LaunchedEffect(true) {
                    reviews = valoracionRepository.getValoracionByIdProtectora(id.toString());
                }
                reviews?.let { Reviews(it, users) }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
fun getDonacionOrgBD(org: Protectora, Donaciones: List<Donacion>): Float {
    var donacion: Float = 0F
    for (i in Donaciones) {
        if (i.idProtectora == org.idProtectora.toString())
            donacion += i.cantidad
    }
    return donacion
}

@OptIn(DelicateCoroutinesApi::class)
fun ExisteUserDonacionBD(
    cliente: Cliente,
    Donaciones: List<Donacion>,
    donacion: Donacion,
    donacionRepository: donacionRepository,
    org: Protectora
): Boolean {
    var existeUsuario: Boolean = false
    for (i in Donaciones) {
        if (i.idCliente == cliente.idCliente && i.idProtectora == org.idProtectora.toString()) {
            existeUsuario = true
            i.cantidad += donacion.cantidad
            GlobalScope.launch {
                donacionRepository.updateDonaciones(
                    i.cantidad,
                    cliente.idCliente,
                    org.idProtectora.toString()
                )
            }
        }
    }
    return existeUsuario
}

@Composable
fun OrgImageBD(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "OrgProfileImage",
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
            .background(color = Color.DarkGray)
    )
}

@Composable
fun OrgInfoBD(org: Protectora) {
    Column() {
        Text(
            org.nombre,
            modifier = Modifier.padding(top = 7.dp, start = 5.dp),
            fontSize = 20.sp
        )
        Text(
            org.ciudad,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 14.sp,
        )
        Text(
            org.pais,
            modifier = Modifier.padding(top = 1.dp, start = 5.dp),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun DetailInfoBD(
    navController: NavController,
    org: Protectora,
    context: Context,
    users: clientRepository,
    donacionRepository: donacionRepository
) {
    if (org.email != null) {
        Text(
            "Email: " + org.email,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }
    //
    //Direcciones de la organizacion
    //
    if (org.numTlf != null) {
        Text(
            "Número de teléfono: " + org.numTlf,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }
    //
    //Pagina web de la organizacion
    //


    //DONACIONES
    val cliente: Cliente
    runBlocking {
        cliente = users.getClienteByEmail(emailActual)
    }
    var openPopUp by remember { mutableStateOf<Boolean>(false) }
    var result by remember { mutableStateOf<List<Donacion>?>(null) }
    LaunchedEffect(true) {
        val query =
            GlobalScope.async(Dispatchers.IO) { donacionRepository.getDonaciones() }
        result = query.await()
    }
    if (result != null) {
        var donacionOrg by remember { mutableStateOf(getDonacionOrgBD(org, result!!)) }
        Text(
            "Donaciones: $donacionOrg €",
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }

    Row {
        Spacer(Modifier.width(10.dp))
        Button(onClick = { openPopUp = true }) {
            Text("Donar")
        }
        Spacer(Modifier.width(10.dp))
        Button(
            onClick = {
                navController.navigate(route = AppScreens.NewAnimalScreen.route + "/" + org.idProtectora.toString())
            }
        ) {
            Text("Añadir Animal")
        }
    }

    if (openPopUp) {
        var texto by remember { mutableStateOf("") }
        Dialog(onDismissRequest = { openPopUp = false }) {
            AlertDialog(
                onDismissRequest = { openPopUp = false },
                title = { Text("Donar") },
                text = {
                    Column {
                        TextField(
                            value = texto,
                            onValueChange = { texto = it },
                            label = { Text("Cantidad") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val newDonation =
                                Donacion(
                                    cliente.idCliente,
                                    org.idProtectora.toString(),
                                    texto.toFloat()
                                )
                            if (!ExisteUserDonacionBD(
                                    cliente,
                                    result!!,
                                    newDonation,
                                    donacionRepository,
                                    org
                                )
                            ) {
                                var listaDonacion: List<Donacion> =
                                    newDonation?.let { listOf(it) }!!
                                GlobalScope.launch {
                                    donacionRepository.setDonaciones(listaDonacion)
                                }
                            }
                            openPopUp = false

                            //
                            // Se puede poner un boton de compartir
                            //
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "He donado a ${org.nombre} $texto € para ayudarla con los animales.\n" +
                                            "¿Te unes a ayudar?\n"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            ContextCompat.startActivity(context, shareIntent, null)
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { openPopUp = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }


}

//
//No hay imagenes en la org, no se usa de momento TODO
//
@Composable
fun OrgGalleryBD(idOrg: String, navController: NavController, animalRepository: animalRepository) {
    var animals by remember { mutableStateOf<List<Animal>?>(null) }
    LaunchedEffect(true) {
        val query = GlobalScope.async(Dispatchers.IO) {
            animalRepository.getAnimalByOrgId(idOrg)
        }
        animals = query.await()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.height(500.dp),
        verticalArrangement = Arrangement.Center
    ) {
        animals?.forEachIndexed { index, animal ->
            item {
                showImageBD(animal, navController)
            }
        }
    }
}

@Composable
fun showImageBD(animal: Animal, navController: NavController) {

    AsyncImage(
        model = animal.fotos,
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "Animal photo",
        modifier = Modifier
            .size(200.dp)
        /*.clickable { TODO detalles del animal
            navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + animal.idAnimal)
        }*/
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReviewsFieldBD(
    id: String,
    users: clientRepository,
    valoracionRepository: valoracionRepository
) {
    var review by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(165, 165, 165), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¿Quieres dejar una opinión sobre esta organización?",
            fontSize = 14.sp
        )

        TextField(
            value = review,
            onValueChange = { review = it },
            placeholder = {
                Text(
                    text = "¿Algo que decir?",
                    modifier = Modifier.alpha(0.5F),
                    fontSize = 12.sp
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { review = "" }
                )
            },
            modifier = Modifier
                .padding(bottom = 5.dp)
                .height(47.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    GlobalScope.launch {
                        val cliente = users.getClienteByEmail(emailActual)
                        valoracionRepository.setValoracion(cliente.idCliente, id, review)
                        review = ""
                        keyboardController?.hide()
                    }
                })
        )
    }

}

@Composable
fun ReviewsBD(reviews: List<Valoracion>, users: clientRepository) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(150.dp)
    ) {
        reviews?.let {
            items(it) { valor ->
                reviewBD(valor, users)
            }
        }
    }
}

@Composable
fun reviewBD(valoracion: Valoracion, users: clientRepository) {

    var cliente by remember { mutableStateOf<Cliente?>(null) }

    LaunchedEffect(true) {
        cliente = users.getClientById(valoracion.idCliente)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(modifier = Modifier.background(Color(209, 209, 209), RoundedCornerShape(8.dp))) {
        Column {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 7.dp, start = 7.dp)
            )
            cliente?.let {
                Text(
                    text = it.nickname,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 5.dp, top = 3.dp, bottom = 4.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = valoracion.valoracion,
                modifier = Modifier.padding(5.dp),
                textAlign = TextAlign.Center,
                softWrap = true,
            )
        }
    }

}
