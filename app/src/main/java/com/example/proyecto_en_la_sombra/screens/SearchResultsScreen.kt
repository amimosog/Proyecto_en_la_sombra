package com.example.proyecto_en_la_sombra.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.Animal
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import com.example.proyecto_en_la_sombra.ui.theme.PurpleGrey40
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


private val photos: List<Photo> = listOf(
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
)

val data = listOf("☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️", "☕️")


@Composable
fun listadoResultados(navController: NavController, search: String) {
    var result1 by remember { mutableStateOf<RemoteModelPage?>(null) }
    var result2 by remember { mutableStateOf<RemoteModelPage?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query1 = GlobalScope.async(Dispatchers.IO) { service.getAnimalsName(auth, "random", search) }
        val query2 = GlobalScope.async(Dispatchers.IO) { service.getAnimalsLocation(auth, "random", search) }
        result1 = query1.await()!!
        result2 = query2.await()!!


    }
    var result : List<Animal>? = null
    if(result1 != null && result2 != null){
    val animals1 = result1!!.animals
    val animals2 = result2!!.animals
        result = animals1 + animals2
    } else if (result1 != null){
        result = result1!!.animals
    } else if (result2 != null){
        result = result2!!.animals
    }

        if (result != null) {
            Text(
                text = "Resultados animales",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp, top = 30.dp)
            ) {
                    items(result) { item ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    navController.navigate(route = AppScreens.AnimalDetailScreen.route)
                                }
                        ) {
                            AsyncImage(
                                model =
                                    if (item.photos.isNotEmpty())
                                        item.photos[0]
                                    else
                                        "https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U",
                                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = "Animal photo",
                                modifier = Modifier
                                    .background(color = PurpleGrey40)
                                    .size(width = 600.dp, height = 800.dp) //Tendria que ocupar toda la pantalla
                            )
                        }

                    }

            }
        }
}


@Preview(showSystemUi = true)
@Composable
fun verPantalla(){
    //listadoResultados()
}