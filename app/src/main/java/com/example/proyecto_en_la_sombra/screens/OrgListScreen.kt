package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import com.example.proyecto_en_la_sombra.ui.theme.PurpleGrey40


data class Org(val name: String, val description: String, val image: Int)

private val orgList: List<Org> = listOf(
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede, Esta organizacion ayuda a encontrar casa a todos los perros que puede, Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground),
    Org(name = "No perros sin hogar", description = "Esta organizacion ayuda a encontrar casa a todos los perros que puede", image = R.drawable.ic_launcher_foreground)
)

@Composable
fun OrganizationList(navController: NavController){
    Row (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ){
        Text(
            text = "Lista de Organizaciones",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
    LazyColumn (
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxWidth()
    ){
        items(orgList){ org ->
            mostrarOrg(org, navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun mostrarOrg(org: Org, navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier
            .fillMaxWidth(0.90f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Gray)
            .padding(5.dp)
            .clickable {
                expanded = !expanded
                navController.navigate(route = AppScreens.ProfileOrganizationScreen.route)
            },
    ) {
        orgLogo(org)
        orgTexts(org, if (expanded) Int.MAX_VALUE else 2)
    }
}

@Composable
fun orgLogo(org: Org){
    Image(
        painterResource(id = org.image),
        "Logo de la organizacion",
        modifier = Modifier
            .size(60.dp)
            .background(color = Color.Transparent)
    )
}

@Composable
fun orgTexts(org: Org, lines: Int = Int.MAX_VALUE){
    Column {
        Text(text = org.name,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = org.description,
            color = Color.Yellow,
            style = MaterialTheme.typography.bodySmall,
            maxLines = lines
        )
    }
}