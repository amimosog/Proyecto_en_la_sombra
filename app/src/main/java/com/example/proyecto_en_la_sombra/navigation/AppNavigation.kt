package com.example.proyecto_en_la_sombra.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_en_la_sombra.screens.FirstScreen
import com.example.proyecto_en_la_sombra.screens.listOfElements
import com.example.proyecto_en_la_sombra.screens.AnimalComponents
import com.example.proyecto_en_la_sombra.screens.ProfileComponents
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.proyecto_en_la_sombra.screens.OrganizationList
import com.example.proyecto_en_la_sombra.screens.SearchBarCustom
import com.example.proyecto_en_la_sombra.screens.SelectCategory
import com.example.proyecto_en_la_sombra.screens.listadoResultados
import com.example.proyecto_en_la_sombra.screens.profileOrganization

/*Elemento composable que se va a encargar de orquestar la navegacion, va a conocer
las pantallas de nuestra app y se va a encargar de gestionar el paso entre ellas*/
@Composable
fun AppNavigation(context : Context){
    val navController = rememberNavController()

    //El elemento NavHost va a conocer las pantallas y como navegar entre ellas
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route){
        //El navHost estara formado por diferente composables que seran cada una de nuestras pantallas
        composable(route = AppScreens.FirstScreen.route){
            FirstScreen(navController)
        }

        composable(route = AppScreens.AnimalListScreen.route){
            listOfElements(navController, context)
            MyNavigationBar(navController)
        }

        composable(route = AppScreens.AnimalDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            it.arguments?.getString("id")?.let { it1 -> AnimalComponents(navController, it1, context) }
        }

        composable(route = AppScreens.ProfileUserScreen.route){
            ProfileComponents(navController, context)
            MyNavigationBar(navController)
        }

        composable(route = AppScreens.SearchScreen.route){
            val list = listOf<String>("test1", "test2", "test3")
            SearchBarCustom(navController)
            SelectCategory(name = "test", list = list)
            MyNavigationBar(navController)
        }

        composable(route = AppScreens.OrgListScreen.route){
            OrganizationList(navController)
            MyNavigationBar(navController)
        }

        composable(route = AppScreens.ProfileOrganizationScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("id")?.let { it1 -> profileOrganization(navController, it1) }

        }
        composable(route = AppScreens.SearchResultsScreen.route + "/{search}",
            arguments = listOf(
                navArgument("search"){
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("search")?.let { it1 -> listadoResultados(navController, it1) }

        }
    }
}