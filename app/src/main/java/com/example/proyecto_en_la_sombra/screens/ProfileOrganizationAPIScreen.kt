package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.Model.Donacion
import com.example.proyecto_en_la_sombra.Model.Valoracion
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.animalRepository
import com.example.proyecto_en_la_sombra.Repository.clientRepository
import com.example.proyecto_en_la_sombra.Repository.donacionRepository
import com.example.proyecto_en_la_sombra.Repository.protectoraRepository
import com.example.proyecto_en_la_sombra.Repository.valoracionRepository
import com.example.proyecto_en_la_sombra.api.model.Animal
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrgRemoteModel
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
fun profileOrganizationAPI(
    navController: NavController,
    id: String,
    context: Context,
    protectoraRepository: protectoraRepository,
    valoracionRepository: valoracionRepository,
    donacionRepository: donacionRepository,
    users: clientRepository,
    animals: animalRepository
) {
    var result by remember { mutableStateOf<OrgRemoteModel?>(null) }
    var reviews by remember { mutableStateOf<List<Valoracion>?>(null) }
    LaunchedEffect(true) {

        val query =
            GlobalScope.async(Dispatchers.IO) {
                protectoraRepository.getUniqueOrganization(id)
            }
        result = query.await()
    }
    Column(Modifier.padding(8.dp)) {

        Row {
            result?.organization?.let {
                if (it.photos.isNotEmpty())
                    OrgImageAPI(it.photos[0].small)
                else OrgImageAPI("https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U")
            }
            result?.let { OrgInfoAPI(it) }
        }


        result?.let { SocialMediaAPI(it) }

        result?.let { DetailInfoAPI(navController, it, context, donacionRepository, users) }

        OrgGalleryAPI(id, navController, animals)
        result?.let { ReviewsFieldAPI(id, users, valoracionRepository) }


        //Se llama a pintar los comentarios
        LaunchedEffect(true) {
            reviews = valoracionRepository.getValoracionByIdProtectora(id);
        }
        reviews?.let { ReviewsBD(it, users) }

    }
}

@SuppressLint("SuspiciousIndentation")
fun getDonacionOrg(org: OrgRemoteModel, Donaciones: List<Donacion>): Float {
    var donacion: Float = 0F
    for (i in Donaciones) {
        if (i.idProtectora == org.organization.id)
            donacion += i.cantidad
    }
    return donacion
}

@OptIn(DelicateCoroutinesApi::class)
fun ExisteUserDonacion(
    cliente: Cliente,
    Donaciones: List<Donacion>,
    donacion: Donacion,
    donacionRepository: donacionRepository,
    org: OrgRemoteModel
): Boolean {
    var existeUsuario: Boolean = false
    for (i in Donaciones) {
        if (i.idCliente == cliente.idCliente && i.idProtectora == org.organization.id) {
            existeUsuario = true
            i.cantidad += donacion.cantidad
            GlobalScope.launch {
                donacionRepository.updateDonaciones(
                    i.cantidad,
                    cliente.idCliente,
                    org.organization.id
                )
            }
        }
    }
    return existeUsuario
}

@Composable
fun OrgImageAPI(url: String) {
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
fun OrgInfoAPI(org: OrgRemoteModel) {
    Column() {
        Text(
            org.organization.name,
            modifier = Modifier.padding(top = 7.dp, start = 5.dp),
            fontSize = 20.sp
        )
        Text(
            org.organization.address.city,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 14.sp
        )
        Text(
            org.organization.address.country,
            modifier = Modifier.padding(top = 1.dp, start = 5.dp),
            fontSize = 12.sp
        )
    }
}

@Composable
fun DetailInfoAPI(
    navController: NavController,
    org: OrgRemoteModel,
    context: Context,
    donacionRepository: donacionRepository,
    users: clientRepository
) {
    if (org.organization.email != null) {
        Text(
            "Email: " + org.organization.email,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }
    if (org.organization.address.address1 != null && org.organization.address.address2 != null) {
        Text(
            "Dirección: " + org.organization.address.address1 + " " + org.organization.address.address2,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    } else if (org.organization.address.address1 != null) {
        Text(
            "Dirección: " + org.organization.address.address1,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    } else if (org.organization.address.address2 != null) {
        Text(
            "Dirección: " + org.organization.address.address2,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }
    if (org.organization.phone != null) {
        Text(
            "Número de teléfono: " + org.organization.phone,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }
    if (org.organization.website != null) {
        Text(
            "Página web: " + org.organization.website,
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp
        )
    }

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
        var donacionOrg by remember { mutableStateOf(getDonacionOrg(org, result!!)) }
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
                navController.navigate(route = AppScreens.NewAnimalScreen.route + "/" + org.organization.id)
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
                                Donacion(cliente.idCliente, org.organization.id, texto.toFloat())
                            if (!ExisteUserDonacion(
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
                                    "He donado a esta organización " + texto + "€ para ayudarla con los animales.\n" +
                                            "¿Te unes a ayudar?\n" + org.organization.url
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(context, shareIntent, null)
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


@Composable
fun OrgGalleryAPI(id: String, navController: NavController, animals: animalRepository) {
    var result by remember { mutableStateOf<RemoteModelPage?>(null) }
    LaunchedEffect(true) {
        val query = //Poner el id de la organizacion
            GlobalScope.async(Dispatchers.IO) { animals.getAnimalsByOrganization(id) }
        result = query.await()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
    ) {
        result?.animals?.forEachIndexed { index, animal ->
            item {
                showImageAPI(animal, navController)
            }
        }
    }
}

@Composable
fun showImageAPI(animal: Animal, navController: NavController) {

    AsyncImage(
        model = animal.photos[0].medium,
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "Animal photo",
        modifier = Modifier
            .size(200.dp)
            .clickable {
                navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + animal.id)
            }
    )
}

@Composable
fun SocialMediaAPI(org: OrgRemoteModel) {
    val localUriHandler = LocalUriHandler.current
    val logoModifier = Modifier
        .padding(top = 5.dp, start = 10.dp)
        .size(60.dp)
        .clip(CircleShape)

    Row {
        if (org.organization.social_media.twitter != null) {
            Image(
                painterResource(id = R.drawable.twitter_logo),
                "twitter_logo",
                logoModifier.clickable { localUriHandler.openUri(org.organization.social_media.twitter.toString()) })
        }
        if (org.organization.social_media.youtube != null) {
            Image(
                painterResource(id = R.drawable.youtube_logo),
                "youtube_logo",
                logoModifier.clickable { localUriHandler.openUri(org.organization.social_media.youtube.toString()) })
        }
        if (org.organization.social_media.facebook != null) {
            Image(
                painterResource(id = R.drawable.facebook_logo),
                "facebook_logo",
                logoModifier.clickable { localUriHandler.openUri(org.organization.social_media.facebook.toString()) })
        }
        if (org.organization.social_media.pinterest != null) {
            Image(
                painterResource(id = R.drawable.pinterest_logo),
                "pinterest_logo",
                logoModifier.clickable { localUriHandler.openUri(org.organization.social_media.pinterest.toString()) }
            )
        }
        if (org.organization.social_media.instagram != null) {
            Image(
                painterResource(id = R.drawable.instagram_logo),
                "instagram_logo",
                logoModifier.clickable { localUriHandler.openUri(org.organization.social_media.instagram.toString()) }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReviewsFieldAPI(
    id: String,
    users: clientRepository,
    valoracionRepository: valoracionRepository
) {
    var review by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(165, 165, 165)),
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
fun Reviews(reviews: List<Valoracion>, users: clientRepository) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        reviews?.let {
            items(it) { valor ->
                review(valor, users)
            }
        }

    }
}

@Composable
fun review(valoracion: Valoracion, users: clientRepository) {

    var cliente by remember { mutableStateOf<Cliente?>(null) }

    LaunchedEffect(true) {
        cliente = users.getClientById(valoracion.idCliente)
    }
    Row {
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