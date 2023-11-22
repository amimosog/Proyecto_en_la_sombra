package com.example.proyecto_en_la_sombra.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MyNavigationBar(navController: NavController){
    Row(modifier = Modifier
        .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        NavigationButton(
            navController = navController,
            route = AppScreens.AnimalDetailScreen.route,
            imageVector = Icons.Default.Lock
        )
        NavigationButton(
            navController = navController,
            route = AppScreens.AnimalListScreen.route,
            imageVector = Icons.Default.Home
        )
        NavigationButton(
            navController = navController,
            route = AppScreens.SearchScreen.route,
            imageVector = Icons.Default.Search
        )
        NavigationButton(
            navController = navController,
            route = AppScreens.ProfileUserScreen.route,
            imageVector = Icons.Default.Person
        )
    }
}


@Composable
fun NavigationButton(navController: NavController, route: String, imageVector: ImageVector){
    Button(
        modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp * 0.25).dp)
            .height(60.dp),
        shape = RoundedCornerShape(0.dp),
        onClick = {
            navController.navigate(route = route)
        }
    ) {
        Icon(imageVector = imageVector, "")
    }
}