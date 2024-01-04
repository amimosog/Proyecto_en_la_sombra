package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Protectora
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.emailActual
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun profileOrganizationBD(navController: NavController, idOrg : Long, context: Context) {
    val room : AplicacionDB = AplicacionDB.getInstance(context)

    var org: Protectora
    runBlocking{
        org = room.protectoraDAO().getOrganizacionId(idOrg)
    }

    Column(Modifier.padding(8.dp)) {
        Column {
            Row {
                /*if(org.photos.isNotEmpty())TODO
                    OrgImageBD(org.photos[0])
                else*/ OrgImageBD("https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U")

                OrgInfoBD(org)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "SocialMediaBD(org)")
        }
        Column {
            DetailInfoBD(org)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //
            //No hay imagenes TODO
            //OrgGalleryBD(idOrg,navController)
            ReviewsFieldBD(org, context, idOrg) }
    }
    Column(modifier = Modifier.padding(0.dp)) {
        ReviewsBD(1)
    }
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
        //
        //Ciudad de la organizacion
        //
        Text(
            "Ciudad",
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 14.sp,
            color = Color.Red
        )
        //
        //Pais de la organizacion
        //
        Text(
            "Pais",
            modifier = Modifier.padding(top = 1.dp, start = 5.dp),
            fontSize = 12.sp,
            color = Color.Red
        )
    }
}

@Composable
fun DetailInfoBD(org: Protectora) {
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
}

//
//No hay imagenes en la org, no se usa de momento TODO
//
@Composable
fun OrgGalleryBD(id: Long, navController: NavController) {
    var result by remember { mutableStateOf<RemoteModelPage?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = //Poner el id de la organizacion
            GlobalScope.async(Dispatchers.IO) { service.getAnimalsByOrganization(auth,id.toString()) }
        result = query.await()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.height(400.dp)
    ) {
        result?.animals?.forEachIndexed { index, animal ->
            item {
                showImageAPI(animal, navController)
            }
        }
    }
}

@Composable
fun ReviewsFieldBD(org: Protectora, context: Context, id : Long) {
    var review by remember { mutableStateOf("") }

    val room : AplicacionDB = AplicacionDB.getInstance(context)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
            .background(color = Color(165, 165, 165))
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¿Quieres dejar una opinión sobre esta organización?",
            fontSize = 14.sp
        )

        TextField(
            value = review,
            onValueChange = {review = it },
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
                .height(47.dp)
                // Add this Modifier
                .onKeyEvent { event ->
                    when (event.key) {
                        Key.Enter -> {
                            // Handle the enter key here
                            GlobalScope.launch {
                                var cliente = room
                                    .clienteDAO()
                                    .getClienteByEmail(emailActual)
                                room
                                    .valoracionDAO()
                                    .setValoracion(cliente.idCliente, id.toString(), review)
                                review = ""
                            }
                            true
                        }

                        else -> false
                    }
                },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ReviewsBD(id: Int) {
    Column(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(10.dp)),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(212, 212, 212))
        ) {
            Row {
                Column {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 7.dp, start = 7.dp)
                    )
                    Text(
                        text = "Nombre de usuario",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp, top = 3.dp, bottom = 4.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Esto es una review",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center,
                        softWrap = true,
                    )
                }
            }
        }
    }
}