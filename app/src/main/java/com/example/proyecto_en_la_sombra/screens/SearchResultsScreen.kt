package com.example.proyecto_en_la_sombra.screens



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.navigation.AppScreens


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
fun listadoResultados(navController: NavController) {

    Text(text = "Resultados animales", textAlign = TextAlign.Center, modifier = Modifier.padding(top = 10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp, top = 30.dp)
        ) {
            items(data) { item ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(route = AppScreens.AnimalDetailScreen.route)
                        }
                ) {
                    Text(
                        text = item,
                        fontSize = 42.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)

                    )
                }
            }
        }
}


@Preview(showSystemUi = true)
@Composable
fun verPantalla(){
    //listadoResultados()
}