package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

/*Funcion que pinta cada elemento de la lista de forma optima*/
@Composable
fun listOfElements(navController: NavController){
    var result by remember { mutableStateOf<RemoteModelPage?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = GlobalScope.async(Dispatchers.IO) { service.getAnimalsRandom(auth, "random") }
        result = query.await()
    }

    if (result != null){
        /*LazyColumn nos permite ser mas eficientes ya que
     * traera a memoria unicamente los elementos que se pueden pintar (los que
     * caben dentro de los margenes de la pantalla)*/
        LazyColumn {
            /*Items es un iterador inteligente que se va a ejecutar
            * una vez por cada elemento de la lista*/
            result?.animals?.let {
                items(it){ animal ->
                    listElement(animal, navController)
                }
            }


        }
    }

}

/*Funcion que pinta los textos y las imagenes en conjunto*/
@Composable
fun listElement(animal: Animal, navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + animal.id)
        }
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        imageElements(animal.photos[0].full)
        iconElements()
        textElements(animal)
    }
}

/*Funcion que pinta los elementos de tipo imagen*/
@Composable
fun imageElements(image: String){
    AsyncImage(
        model = image,
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "Animal photo",
        modifier = Modifier
            .background(color = PurpleGrey40)
            .size(width = 600.dp, height = 800.dp) //Tendria que ocupar toda la pantalla
    )
}

/*Funcion que pinta los elementos de tipo texto*/
@Composable
fun iconElements(){
    Column {
        Icon(Icons.Default.Add,
            contentDescription = "Follow button",
            modifier = Modifier
                .padding(start = 350.dp, top = 630.dp)
                .size(30.dp))
        Icon(Icons.Default.FavoriteBorder,
            contentDescription = "like button",
            modifier = Modifier
                .padding(start = 350.dp, top = 10.dp)
                .size(30.dp))
        Icon(Icons.Default.Share,
            contentDescription = "like button",
            modifier = Modifier
                .padding(start = 350.dp, top = 10.dp)
                .size(30.dp))
    }
}

@Composable
fun textElements(animal: Animal){
    Column {
        Text("Localización Organización",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp))
        Text(animal.organization_id.toString(),
            modifier = Modifier.padding(start = 20.dp))
        Text(animal.name,
            modifier = Modifier.padding(top = 660.dp, start = 20.dp))
        if(animal.description != null) Text(animal.description,
            modifier = Modifier.padding(start = 20.dp))
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun viewFuction(){
//    Proyecto_en_la_sombraTheme{ //Necesario para poder usar las propiedades de MaterialTheme
//        listOfElements(allTheTexts)
//    }
//}
//
//@Preview
//@Composable
//fun viewComponent(){
//    val element: List<Texts> = listOf(Texts("Nombre del chucho","Descripcion del chucho"))
//    listOfElements(element)
//}