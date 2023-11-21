package com.example.proyecto_en_la_sombra.Views

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.ui.theme.Proyecto_en_la_sombraTheme
import com.example.proyecto_en_la_sombra.ui.theme.PurpleGrey40

//Constante creado para probar que se pinta correctamente la interfaz
private val allTheTexts: List<Texts> = listOf(Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"),
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
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho"));

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_en_la_sombraTheme{
                listOfElements(allTheTexts)
            }
        }
    }
}

data class Texts(val title: String, val descripion: String) //Clase que representa a los textos de cada elemento

/*Funcion que pinta cada elemento de la lista de forma optima*/
@Composable
fun listOfElements(allTheTexts: List<Texts>){

    /*LazyColumn nos permite ser mas eficientes ya que
    * traera a memoria unicamente los elementos que se pueden pintar (los que
    * caben dentro de los margenes de la pantalla)*/
    LazyColumn(){
        /*Items es un iterador inteligente que se va a ejecutar
        * una vez por cada elemento de la lista*/
        items(allTheTexts){ textsElement ->
            listElement(textsElement)
        }
    }
}
/*Funcion que pinta los textos y las imagenes en conjunto*/
@Composable
fun listElement(myTexts: Texts){
    Box(modifier = Modifier.fillMaxSize()) {
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
    Column() {
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
        Text(myTexts.descripion,
            modifier = Modifier.padding(start = 20.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun viewFuction(){
    Proyecto_en_la_sombraTheme{ //Necesario para poder usar las propiedades de MaterialTheme
        listOfElements(allTheTexts)
    }
}

@Preview
@Composable
fun viewComponent(){
    val element: List<Texts> = listOf(Texts("Nombre del chucho","Descripcion del chucho"))
    listOfElements(element)
}