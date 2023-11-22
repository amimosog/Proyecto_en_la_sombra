package com.example.proyecto_en_la_sombra.navigation

/*Esta clase se utiliza para centralizar las pantallas*/
sealed class AppScreens(val route: String){
    //Aqui se definiran las pantallas que forman nuestra aplicacion
    //Con esto se consigue tipar y limitar las pantallas de nuestra aplicacion, para que en el momento en el que
    // se quieran realizar navegaciones solo se podran realizar a las pantallas que tenemos aqui definidas
    object FirstScreen: AppScreens("first_screen")
    object ListScreen: AppScreens("list_screen")
}