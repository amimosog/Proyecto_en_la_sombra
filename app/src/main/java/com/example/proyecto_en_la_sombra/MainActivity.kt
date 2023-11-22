package com.example.proyecto_en_la_sombra


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.organizationsModel.Organization
import com.example.proyecto_en_la_sombra.navigation.AppNavigation
import kotlinx.coroutines.launch

private const val grant_type = "client_credentials"
private const val client_id = "jt78yfZFePKyGM8tmLpHZduPe4wiobusxA4gvGrxv5p9xlMREy"
private const val client_secret = "ADto7Ake8ThsPUe57IprbsLHORd29qhZynDdw1ej"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        lifecycleScope.launch {

            val authResponse = service.login(grant_type,client_id,client_secret)
            val auth = "Bearer ${authResponse.access_token}"
            println(auth)
            val animal = service.getAnimals(auth,"69771579")
            println(animal)

            val listanimals = service.getAnimalsRandom(auth,"random")
            println(listanimals)

            val listOrganizations = service.getOrganizations(auth)
            println(listOrganizations)

            val Organization = service.getUniqueOrganization(auth,"WI535")
            println(Organization)
        }
            setContent {
                AppNavigation()
            }
    }
}



@Preview(showSystemUi = true)
@Composable
fun preview(){
    AppNavigation()
}