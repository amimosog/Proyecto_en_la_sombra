package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.Animal
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrgRemoteModel
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.api.organizationsModel.Organization
import com.example.proyecto_en_la_sombra.api.organizationsModel.Photo
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.emailActual
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun profileOrganization(navController: NavController, id : String, context: Context) {
    var result by remember { mutableStateOf<OrgRemoteModel?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query =
            GlobalScope.async(Dispatchers.IO) { service.getUniqueOrganization(auth, id) }
        result = query.await()
    }
    Column(Modifier.padding(8.dp)) {
        Column {
            Row {
                result?.organization?.let {
                    if(it.photos.isNotEmpty())
                        UserImage(it.photos[0].small)
                    else UserImage("https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U")
                }
                result?.let { UserInfo(it) }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            result?.let { SocialMedia(it) }
        }
        Column {
            result?.let { DetailInfo(it) }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            UserGallery(id,navController)
            result?.let { ReviewsField(it, context, id) }
        }
        Column(modifier = Modifier.padding(0.dp)) {
            Reviews(1)
        }
    }
}

@Composable
fun UserImage(url: String) {
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
fun UserInfo(org: OrgRemoteModel) {
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
fun DetailInfo(org: OrgRemoteModel) {
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
}

@Composable
fun UserGallery(id:String, navController: NavController) {
    var result by remember { mutableStateOf<RemoteModelPage?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = //Poner el id de la organizacion
            GlobalScope.async(Dispatchers.IO) { service.getAnimalsByOrganization(auth,id) }
        result = query.await()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.height(400.dp)
    ) {
        result?.animals?.forEachIndexed { index, animal ->
            item {
                showImage(animal, navController)
            }
        }
    }
}
@Composable
fun showImage(animal : Animal, navController : NavController){

        AsyncImage(
            model = animal.photos[0].medium,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Animal photo",
            modifier = Modifier.size(200.dp).clickable {
                navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + animal.id)
            }
        )
}

@Composable
fun SocialMedia(org: OrgRemoteModel) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsField(org: OrgRemoteModel, context: Context, id : String) {
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
                                var cliente = room.clienteDAO().getClienteByEmail(emailActual)
                                room.valoracionDAO().setValoracion(cliente.idCliente,id,review)
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
fun Reviews(id: Int) {
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
