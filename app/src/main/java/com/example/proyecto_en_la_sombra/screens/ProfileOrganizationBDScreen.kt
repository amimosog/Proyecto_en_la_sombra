package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.Model.Donacion
import com.example.proyecto_en_la_sombra.Model.Protectora
import com.example.proyecto_en_la_sombra.Model.Valoracion
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrgRemoteModel
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
fun profileOrganizationBD(navController: NavController, id : Long, context: Context) {
    var reviews by remember { mutableStateOf<List<Valoracion>?>(null) }
    val room : AplicacionDB = AplicacionDB.getInstance(context)

    var org: Protectora
    runBlocking{
        org = room.protectoraDAO().getOrganizacionId(id)
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
        Column {
            DetailInfoBD(org, context)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //
            //No hay imagenes TODO
            //OrgGalleryBD(idOrg,navController)
            ReviewsFieldBD(org, context, id.toString()) }
    }
    //Se llama a pintar los comentarios
    LaunchedEffect(true) {
        reviews = room.valoracionDAO().getValoracionByIdProtectora(id.toString());
    }
    Reviews(reviews,context)

}

@SuppressLint("SuspiciousIndentation")
fun getDonacionOrgBD(org : Protectora, Donaciones : List<Donacion>): Float {
    var donacion : Float = 0F
    for (i in Donaciones){
        if(i.idProtectora == org.idProtectora.toString())
            donacion += i.cantidad
    }
    return donacion
}

@OptIn(DelicateCoroutinesApi::class)
fun ExisteUserDonacionBD(cliente : Cliente, Donaciones : List<Donacion>, donacion: Donacion, room : AplicacionDB, org: Protectora) : Boolean {
    var existeUsuario : Boolean = false
    for (i in Donaciones) {
        if(i.idCliente == cliente.idCliente && i.idProtectora == org.idProtectora.toString()) {
            existeUsuario = true
            i.cantidad += donacion.cantidad
            GlobalScope.launch { room.donacionDAO().updateDonacion(i.cantidad, cliente.idCliente, org.idProtectora.toString()) } }
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
        //
        //Ciudad de la organizacion
        //
        Text(
            org.ciudad,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 14.sp,
        )
        //
        //Pais de la organizacion
        //
        Text(
            org.pais,
            modifier = Modifier.padding(top = 1.dp, start = 5.dp),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun DetailInfoBD(org: Protectora, context: Context) {
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
    val room = AplicacionDB.getInstance(context)
    val cliente : Cliente
    runBlocking{
        cliente = room.clienteDAO().getClienteByEmail(emailActual)
    }
    var openPopUp by remember { mutableStateOf<Boolean>(false) }
    var result by remember { mutableStateOf<List<Donacion>?>(null) }
    LaunchedEffect(true) {
        val query =
            GlobalScope.async(Dispatchers.IO) { room.donacionDAO().getDonaciones() }
        result = query.await()
    }
    if (result != null) {
        var donacionOrg by remember { mutableStateOf(getDonacionOrgBD(org,result!!)) }
        Text(
            "Donaciones: $donacionOrg €",
            modifier = Modifier.padding(top = 7.dp, start = 7.dp),
            fontSize = 14.sp)
        Button(onClick = { openPopUp = true }) {
            Text("Donar")
        }
    }
    if(openPopUp){
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
                            val newDonation = Donacion(cliente.idCliente, org.idProtectora.toString(), texto.toFloat())
                            if(!ExisteUserDonacionBD(cliente, result!!, newDonation, room, org)) {
                                var listaDonacion : List<Donacion> = newDonation?.let { listOf(it) }!!
                                GlobalScope.launch {
                                    room.donacionDAO().setDonaciones(listaDonacion)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsFieldBD(org: Protectora, context: Context, id : String) {
    var review by remember { mutableStateOf("") }

    val room : AplicacionDB = AplicacionDB.getInstance(context)

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
                                    .setValoracion(cliente.idCliente, id, review)
                                review = ""
                            }
                            true
                        }

                        else -> false
                    }
                },
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
fun ReviewsBD(reviews: List<Valoracion>?, context: Context) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 33.dp, bottom = 60.dp)
            .fillMaxWidth()
    ) {
        reviews?.let {
            items(it) { valor ->
                reviewBD(valor, context)
            }
        }

    }
}

@Composable
fun reviewBD(valoracion: Valoracion, context: Context) {
    val room : AplicacionDB = AplicacionDB.getInstance(context)
    var cliente by remember { mutableStateOf<Cliente?>(null) }

    LaunchedEffect(true) {
        cliente = room.clienteDAO().getClientById(valoracion.idCliente)
    }
    Column(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(10.dp))
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
    }
}