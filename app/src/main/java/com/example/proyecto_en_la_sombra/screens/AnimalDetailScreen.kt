package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Favoritos
import com.example.proyecto_en_la_sombra.Model.SolicitudAdopcion
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.Repository.animalRepository
import com.example.proyecto_en_la_sombra.Repository.clientRepository
import com.example.proyecto_en_la_sombra.Repository.favoritosRepository
import com.example.proyecto_en_la_sombra.Repository.solicitudAdopciónRepository
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.Animal
import com.example.proyecto_en_la_sombra.api.model.Photo
import com.example.proyecto_en_la_sombra.api.model.RemoteResult
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.emailActual
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimalComponents(
    navController: NavController,
    id: String,
    context: Context,
    solicitudesAdopt: solicitudAdopciónRepository,
    favoritosRepository: favoritosRepository,
    users: clientRepository
) {
    var result by remember { mutableStateOf<RemoteResult?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = GlobalScope.async(Dispatchers.IO) { service.getAnimals(auth, id) }
        result = query.await()
    }

    if (result != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background_register),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val lista = result?.animal?.let { it.photos }
            if (lista != null) {
                Animal_Photo(lista)
            }

            Row(
                modifier = Modifier
                    .padding(top = 50.dp, start = 10.dp, end = 10.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.98F)
            ) {
                result?.animal?.let {
                    Animal_Info(it)
                    Animal_Adopt_ButtonAndLikeIcon(it, context, solicitudesAdopt, favoritosRepository, users)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Animal_Adopt_ButtonAndLikeIcon(
    animal: Animal,
    context: Context,
    solicitudAdopt: solicitudAdopciónRepository,
    favoritosRepository: favoritosRepository,
    users: clientRepository
) {
    //Logica del boton de ADOPCION----------------------------------------------------------------------------------------------
    var isButtonAdoptar by rememberSaveable { mutableStateOf(false) }
    //Variable para gestionar cuando mostrar o no el popup
    var openPopUp by remember { mutableStateOf(false) }
    /* Inicializacion de los likes, si al animal ya se le ha dado like previamente, inicializa
    la variable islikeClicked para que pinte el corazon en consecuencia*/
    var resultSolicitud by remember { mutableStateOf<SolicitudAdopcion?>(null) }
    LaunchedEffect(true) {
        GlobalScope.async(Dispatchers.IO) {
            //La peticion a la base de datos de forma asincrona
            //Inserta en la base de datos si sabe que no existe el id de dicho animal en la bd
            val query = GlobalScope.async(Dispatchers.IO) {
                solicitudAdopt.getAnimalAdop(animal.id.toLong())
            }
            resultSolicitud = query.await()
        }
    }
    if (resultSolicitud == null) {
        isButtonAdoptar = true
    } else
        isButtonAdoptar = false

    //Adoptar
    Button(
        onClick = {
            if (isButtonAdoptar) {
                isButtonAdoptar = false
                openPopUp = true
            }
        },
        modifier = Modifier
            /*.width(120.dp)*/
            .padding(top = 50.dp)
    )
    {
        if (openPopUp) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
            val currentdate = sdf.format(Date())

            AlertDialog(
                onDismissRequest = { openPopUp = false; isButtonAdoptar = true },
                /*alignment = Alignment.Center,
                offset = IntOffset(-370, -675),*/
                properties = DialogProperties(
                    /*focusable = true,*/
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(250.dp, 250.dp)
                        .padding(top = 5.dp)
                        .background(Color(0xFFefe9f4), RoundedCornerShape(8.dp))
                    /*.border(1.dp, Color.Black, RoundedCornerShape(10.dp))*/
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    ) {
                        Spacer(Modifier.height(5.dp))
                        Text(text = "Vas a adoptar a:", color = Color.Black)
                        Text(text = animal.name, color = Color.Black, fontWeight = FontWeight(800))
                        Divider(modifier = Modifier.border(1.dp, Color.Black))
                        Spacer(Modifier.height(20.dp))
                        Text(text = "La fecha de adopción es:", color = Color.Black)
                        Text(
                            text = currentdate.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight(800)
                        )
                        Divider(modifier = Modifier.border(1.dp, Color.Black))
                        Spacer(Modifier.height(30.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            TextButton(
                                onClick = {
                                    //Se solicita la adopcion
                                    GlobalScope.launch {
                                        //La peticion a la base de datos de forma asincrona
                                        solicitudAdopt.setSolicitudCompleta(
                                            users.getClienteByEmail(emailActual).idCliente,
                                            animal.id.toLong(),
                                            currentdate
                                        )

                                        var solicitud = solicitudAdopt.getSolicitud(
                                            users.getClienteByEmail(emailActual).idCliente,
                                            animal.id.toLong()
                                        )
                                        Log.i("Solicitud adopcion:", solicitud.id.toString())
                                        openPopUp = false
                                        Log.i("ACEPTAR", "Animal adoptado")

                                        //
                                        // Se puede poner un boton de compartir
                                        //
                                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                "Acabo de adoptar a este bicho y le he dado una segunda oportunidad, estabilada y toma ejemplo.\n" +
                                                        "A que es bonito?\n" + animal.url
                                            )
                                            type = "text/plain"
                                        }
                                        val shareIntent = Intent.createChooser(sendIntent, null)
                                        startActivity(context, shareIntent, null)
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 20.dp),
                                shape = RectangleShape

                            ) {
                                Text(text = "Confirmar")
                            }

                            Spacer(Modifier.width(10.dp))
                            TextButton(
                                onClick = {
                                    isButtonAdoptar = true

                                    openPopUp = false
                                    Log.i("DENEGAR", "Animal no adoptado")
                                },
                                modifier = Modifier
                                    .padding(top = 20.dp),
                                shape = RectangleShape
                            ) {
                                Text(text = "Denegar")
                            }
                        }

                    }
                }
            }


            //PopUpAdoptar(animal, room, isButtonAdoptar, openPopUp)
        }

        if (isButtonAdoptar)
            Text(text = "Adoptar")
        else
            Text(text = "Solicitado")
    }

    //Logica del LIKE----------------------------------------------------------------------------------------------
    var islikeClicked by rememberSaveable { mutableStateOf(false) }
    /* Inicializacion de los likes, si al animal ya se le ha dado like previamente, inicializa
    la variable islikeClicked para que pinte el corazon en consecuencia*/
    var result by remember { mutableStateOf<Favoritos?>(null) }
    LaunchedEffect(true) {
        GlobalScope.async(Dispatchers.IO) {
            //La peticion a la base de datos de forma asincrona
            //Inserta en la base de datos si sabe que no existe el id de dicho animal en la bd
            val query = GlobalScope.async(Dispatchers.IO) {
                favoritosRepository.getFavByIdAnimal(animal.id.toLong(), users.getClienteByEmail(emailActual).idCliente)
            }
            result = query.await()

            Log.i("Like Result", result.toString())
        }
    }
    if (result != null) {
        islikeClicked = true
    } else {
        islikeClicked = false
    }

    //Like
    IconButton(
        onClick = {
            islikeClicked = !islikeClicked
            if (!islikeClicked) {
                //ha pulsado sobre no me gusta, luego lo elimina de la
                GlobalScope.launch {
                    //La peticion a la base de datos de forma asincrona
                    //Elimina de la base de la tabla favoritos, dicho animal
                    favoritosRepository.deleteFav(Favoritos(users.getClienteByEmail(emailActual).idCliente, animal.id.toLong()))
                    var favoritos: List<Favoritos> = favoritosRepository.getFavsByIdClient(users.getClienteByEmail(emailActual).idCliente)

                    Log.i("Numero de favs ", favoritos.size.toString())
                }
            } else {
                GlobalScope.launch {
                    //La peticion a la base de datos de forma asincrona
                    //Elimina de la base de la tabla favoritos, dicho animal
                    favoritosRepository.setFav(Favoritos(users.getClienteByEmail(emailActual).idCliente, animal.id.toLong()))
                    var favoritos: List<Favoritos> = favoritosRepository.getFavsByIdClient(users.getClienteByEmail(emailActual).idCliente)

                    Log.i("Numero de favs ", favoritos.size.toString())
                }
            }
        },
        modifier = Modifier
            .padding(top = 10.dp)
    ) {

        if (!islikeClicked) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "like button no"
            )

        } else {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "like button si"
            )
        }
    }
}

@Composable
fun Animal_Info(animal: Animal) {
    Column(Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp)) {
        Text(
            text = animal.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = if (animal.description != null)
                animal.description
            else
                "No hay descripción",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth(0.65f),

            )

        val caracteristicas = hashMapOf(
            "Edad" to animal.age,
            "Pelaje" to animal.coat,
            "Género" to animal.gender,
            "Tamaño" to animal.size,
            "Especie" to animal.species,
            "Tipo" to animal.type,
        )

        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
        ) {
            items(caracteristicas.toList()) { (key, value) ->
                if (value != null)
                    Text(
                        "$key: $value",
                        fontSize = 20.sp,
                    )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Animal_Photo(photos: List<Photo>) {
    var openPopUp by remember { mutableStateOf(false) }
    var photoUrl by remember { mutableStateOf<String?>(null) }
    Spacer(Modifier.height(10.dp))
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(400.dp)
    ) {
        items(photos) { photo ->
            AsyncImage(
                model = photo.full,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        openPopUp = true
                        photoUrl = photo.full
                    },
                contentDescription = "Animal photo"
            )
        }
    }
    if (openPopUp) {
        photoUrl?.let { Log.println(Log.ASSERT, "619", it) }
        AlertDialog(
            onDismissRequest = { openPopUp = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
        ) {
            AsyncImage(
                model = photoUrl,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Animal photo",
            )

        }
    }
}