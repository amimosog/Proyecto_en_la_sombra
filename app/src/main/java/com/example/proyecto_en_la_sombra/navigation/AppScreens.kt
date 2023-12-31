package com.example.proyecto_en_la_sombra.navigation

/*Esta clase se utiliza para centralizar las pantallas*/
sealed class AppScreens(val route: String){
    //Aqui se definiran las pantallas que forman nuestra aplicacion
    //Con esto se consigue tipar y limitar las pantallas de nuestra aplicacion, para que en el momento en el que
    // se quieran realizar navegaciones solo se podran realizar a las pantallas que tenemos aqui definidas


    //Pantalla al iniciar la App
    object FirstScreen: AppScreens("first_screen")

    //Pantalla Home y lista aleatoria de animales
    object AnimalListScreen: AppScreens("animal_list_screen")

    //Pantalla de los detalles de un animal concreto
    object AnimalDetailScreen: AppScreens("animal_detail_screen")

    //Pantalla del perfil del usuario
    object ProfileUserScreen: AppScreens("profile_user_screen")

    //Pantalla de busqueda y filtrado
    object SearchScreen: AppScreens("search_screen")

    //Pantalla que muestra la lista de organizaciones
    object OrgListScreen: AppScreens("org_list_screen")

    //Pantalla del perfil del usuario
    object ProfileOrganizationScreen: AppScreens("profile_organization_screen")

    //Pantalla que muestra los resultados de la busqueda o filtrado
    object SearchResultsScreen: AppScreens("search_results_screen")

    //Pantalla para login
    object LoginActivity: AppScreens("loginActivity")

    //Pantalla para el registro
    object RegisterActivity: AppScreens("registerActivity")
}
