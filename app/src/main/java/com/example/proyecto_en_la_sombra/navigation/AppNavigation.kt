package com.example.proyecto_en_la_sombra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_en_la_sombra.screens.FirstScreen
import com.example.proyecto_en_la_sombra.screens.Texts
import com.example.proyecto_en_la_sombra.screens.listOfElements

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
    Texts("Este es el nombre del bicho","Esta es la descripcion del bicho")
);

/*Elemento composable que se va a encargar de orquestar la navegacion, va a conocer
las pantallas de nuestra app y se va a encargar de gestionar el paso entre ellas*/
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    //El elemento NavHost va a conocer las pantallas y como navegar entre ellas
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route){
        //El navHost estara formado por diferente composables que seran cada una de nuestras pantallas
        composable(route = AppScreens.FirstScreen.route){
            FirstScreen(navController)
        }
        composable(route = AppScreens.ListScreen.route){
            listOfElements(navController, allTheTexts)
        }
    }
}