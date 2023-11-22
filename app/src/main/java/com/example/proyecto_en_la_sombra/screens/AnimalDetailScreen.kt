package com.example.proyecto_en_la_sombra.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyecto_en_la_sombra.ui.theme.Proyecto_en_la_sombraTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.R

/*class AnimalView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_en_la_sombraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimalComponents()
                }
            }
        }
    }
}*/

@Composable
fun AnimalComponents(navController: NavController){
    Column (modifier = Modifier.fillMaxSize()) {
        val Lista = listOf<Int>(
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground)
        Animal_Photo(Lista)
            Row (modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 10.dp)){
                val caracteristicas = hashMapOf(
                    "Sexo" to "Masculino",
                    "Pelaje" to "Blanco",
                    "Raza" to "Chihuahua",
                    "Edad" to "3 años",
                    "Peso" to "2 kg",
                    "Ojos" to "Negros",
                )
                val descripcion ="Pipo es un perro bueno y cariñoso"
                Animal_Info("Pipo",descripcion,caracteristicas, Modifier)
                Animal_Adopt_Button()
        }
    }
}
@Composable
fun Animal_Adopt_Button(modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .width(110.dp)
            .padding(top = 50.dp) ) {
        Text(text = "Adoptar")

    }
}

@Composable
fun Animal_Info(name : String, descripcion : String ,caracteristicas: HashMap<String,String>, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "$name", fontSize = 25.sp, fontWeight = FontWeight.Bold
        )
        Text(
            text = "$descripcion", fontSize = 15.sp
        )
        LazyColumn (modifier = Modifier.padding(15.dp)){
            items(caracteristicas.toList()) { (key, value) ->
                Text(
                    "$key: $value",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun Animal_Photo(photos : List<Int>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(400.dp)
    ){
        items(photos) { photo ->
            Image(painter = painterResource(photo),
                contentDescription = "Animal Photo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(200.dp))
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Animal_InfoPreview2() {
    Proyecto_en_la_sombraTheme {
        //AnimalComponents()
    }
}