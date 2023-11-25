package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.organizationsModel.Organization
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrganizationsRemoteModel
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Composable
fun OrganizationList(navController: NavController){
    var result by remember { mutableStateOf<OrganizationsRemoteModel?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = GlobalScope.async(Dispatchers.IO) { service.getOrganizations(auth) }
        result = query.await()
    }

    if (result != null){
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
            result?.organizations?.let {
                items(it){ org ->
                    mostrarOrg(org, navController)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

}

@Composable
fun mostrarOrg(org: Organization, navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier
            .fillMaxWidth(0.90f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Gray)
            .padding(5.dp)
            .clickable {
                expanded = !expanded
                navController.navigate(route = AppScreens.ProfileOrganizationScreen.route + "/" + org.id)
            },
    ) {
        orgLogo(org)
        Spacer(modifier = Modifier.width(10.dp))
        orgTexts(org)
    }
}

@Composable
fun orgLogo(org: Organization){
    AsyncImage(
        model = if(org.photos.isNotEmpty())
                    org.photos[0].full
                else
                    "https://play-lh.googleusercontent.com/QuYkQAkLt5OpBAIabNdIGmd8HKwK58tfqmKNvw2UF69pb4jkojQG9za9l3nLfhv2N5U",
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = "Organization logo",
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(color = Color.Transparent)
    )
}

@Composable
fun orgTexts(org: Organization){
    Column {
        Text(text = if(org.name != null)
                        org.name
                    else
                        "No tiene un nombre asignado",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = if(org.phone != null)
                        org.phone
                    else
                        "No tiene un telefono asignado",
            color = Color.Blue,
            style = MaterialTheme.typography.bodySmall
        )
        Text(text = if(org.email != null)
                        org.email
                    else
                        "No tiene un email asignado",
            color = Color.Yellow,
            style = MaterialTheme.typography.bodySmall
        )
    }
}