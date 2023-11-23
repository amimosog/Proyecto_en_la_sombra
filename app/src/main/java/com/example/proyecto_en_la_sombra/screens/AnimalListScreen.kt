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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import com.example.proyecto_en_la_sombra.ui.theme.PurpleGrey40

//Constante creado para probar que se pinta correctamente la interfaz
private val allTheTexts: List<Texts> = listOf(
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"))


data class Texts(val title: String, val description: String) //Clase que representa a los textos de cada elemento

/*Funcion que pinta cada elemento de la lista de forma optima*/
@Composable
fun listOfElements(navController: NavController, allTheTexts: List<Texts>){

    /*LazyColumn nos permite ser mas eficientes ya que
    * traera a memoria unicamente los elementos que se pueden pintar (los que
    * caben dentro de los margenes de la pantalla)*/
    LazyColumn {
        /*Items es un iterador inteligente que se va a ejecutar
        * una vez por cada elemento de la lista*/
        items(allTheTexts){ textsElement ->
            listElement(textsElement, navController)
        }
    }
}
/*Funcion que pinta los textos y las imagenes en conjunto*/
@Composable
fun listElement(myTexts: Texts, navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            navController.navigate(route = AppScreens.AnimalDetailScreen.route)
        }
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        imageElements()
        iconElements()
        textElements(myTexts)
    }
}

/*Funcion que pinta los elementos de tipo imagen*/
@Composable
fun imageElements(){
    Image(
        painterResource(id = R.drawable.ic_launcher_foreground),
        "Imagen del animal",
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
fun textElements(myTexts: Texts){
    Column {
        Text("Ubicacion del animal", modifier = Modifier.padding(top = 10.dp, start = 20.dp))
        Text("Organizacion que cuida del animal", modifier = Modifier.padding(start = 20.dp))
        Text(text = myTexts.title,
            modifier = Modifier.padding(top = 660.dp, start = 20.dp))
        Text(myTexts.description,
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