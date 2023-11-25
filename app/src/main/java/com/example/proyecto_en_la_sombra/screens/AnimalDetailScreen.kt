package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.Photo
import com.example.proyecto_en_la_sombra.api.model.RemoteResult
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.ui.theme.Proyecto_en_la_sombraTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async



/*class AnimalView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val timeSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        lifecycleScope.launch {
            auth = sharedPreferences.getString("token", "")!!
            val time = sharedPreferences.getLong("time", 0)
            //Check if an hour has passed since the last token was generated, if so, generate a new one.
            if (timeSeconds > (time + 3600)) {
                val authResponse = service.login(grant_type, client_id, client_secret)
                auth = "Bearer ${authResponse.access_token}"
                Log.i("Generated token: ", auth)
                sharedPreferences.edit().putString("token", auth).apply()
                sharedPreferences.edit().putLong("time", timeSeconds).apply()
            }
            //Get the data from the API of the animals and organizations
            val animal = service.getAnimals(auth, "69771579")


            val listanimals = service.getAnimalsRandom(auth, "random")


            val listOrganizations = service.getOrganizations(auth)


            val Organization = service.getUniqueOrganization(auth, "WI535")

        }
        setContent {
            Proyecto_en_la_sombraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val animal
                    AnimalComponents(rememberNavController(), animal)
                }
            }
        }
    }
}*/

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimalComponents(navController: NavController, id: String) {
    var result by remember { mutableStateOf<RemoteResult?>(null) }
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val query = GlobalScope.async(Dispatchers.IO) { service.getAnimals(auth, id) }
        result = query.await()
    }
    if (result != null){
        Column(modifier = Modifier.fillMaxSize()) {
            val lista = result?.animal?.let {it.photos}
            if (lista != null) {
                Animal_Photo(lista)
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 10.dp)
            ) {
                val caracteristicas = hashMapOf(
                    "Sexo" to "Masculino",
                    "Pelaje" to "Blanco",
                    "Raza" to "Chihuahua",
                    "Edad" to "3 años",
                    "Peso" to "2 kg",
                    "Ojos" to "Negros",
                )
                val descripcion = "Pipo es un perro bueno y cariñoso"
                result?.animal?.let { Animal_Info(it.name, it.description, caracteristicas) }
                Animal_Adopt_Button()
            }
        }
    }
}


@Composable
fun Animal_Adopt_Button(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .width(110.dp)
            .padding(top = 50.dp)
    ) {
        Text(text = "Adoptar")

    }
}

@Composable
fun Animal_Info(name: String, descripcion: String, caracteristicas: HashMap<String, String>) {
    Column {
        Text(
            text = name, fontSize = 25.sp, fontWeight = FontWeight.Bold
        )

        Text(
            text = descripcion, fontSize = 15.sp
        )
        LazyColumn(modifier = Modifier.padding(15.dp)) {
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
fun Animal_Photo(photos: List<Photo>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(400.dp)
    ) {
        items(photos) { photo ->
            AsyncImage(
                model = photo.full,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier.size(200.dp),
                contentDescription = "Animal photo",
            )
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