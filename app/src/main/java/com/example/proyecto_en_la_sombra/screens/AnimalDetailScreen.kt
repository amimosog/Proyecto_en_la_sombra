package com.example.proyecto_en_la_sombra.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Favoritos
import com.example.proyecto_en_la_sombra.Model.SolicitudAdopcion
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.Animal
import com.example.proyecto_en_la_sombra.api.model.Photo
import com.example.proyecto_en_la_sombra.api.model.RemoteResult
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.ui.theme.Proyecto_en_la_sombraTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
fun AnimalComponents(navController: NavController, id: String, context : Context) {
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
                    .padding(top = 50.dp, start = 10.dp)
            ) {
                result?.animal?.let {
                    Animal_Info(it)
                    Animal_Adopt_ButtonAndLikeIcon(it, context)
                }
            }
        }
    }
}


@Composable
fun Animal_Adopt_ButtonAndLikeIcon(animal : Animal, context: Context) {

    //Logica del boton de suscripcion
    val room : AplicacionDB = AplicacionDB.getInstance(context)
    var isButtonClicked by rememberSaveable  { mutableStateOf(false) }
    /* Inicializacion de los likes, si al animal ya se le ha dado like previamente, inicializa
    la variable islikeClicked para que pinte el corazon en consecuencia*/
    var resultSolicitud by remember { mutableStateOf<SolicitudAdopcion?>(null) }
    LaunchedEffect(true) {
        GlobalScope.async(Dispatchers.IO) {
            //La peticion a la base de datos de forma asincrona
            //Inserta en la base de datos si sabe que no existe el id de dicho animal en la bd
            val query = GlobalScope.async(Dispatchers.IO) {
                room.solicitudAdopcionDAO().getSolicitud(1.toLong(),animal.id.toLong())
            }
            resultSolicitud = query.await()
        }
    }
    if(resultSolicitud!=null){
        isButtonClicked = true
    } else isButtonClicked = false

    Button(onClick = {
        isButtonClicked= !isButtonClicked
        if (isButtonClicked) {
            //Se solicita la adopcion
            GlobalScope.launch {
                //La peticion a la base de datos de forma asincrona
                room.solicitudAdopcionDAO().setSolicitud(1.toLong(),animal.id.toLong())

                var solicitud = room.solicitudAdopcionDAO().getSolicitud(1.toLong(),animal.id.toLong())
                Log.i("Solicitud adopcion:", solicitud.id.toString())
            }
        }else{ //Desolicitar adopcion
            GlobalScope.launch {
                //La peticion a la base de datos de forma asincrona
                room.solicitudAdopcionDAO().setSolicitud(1.toLong(),animal.id.toLong())

                room.solicitudAdopcionDAO().removeSolicitud(1.toLong(),animal.id.toLong())
                Log.i("Solicitud de adopcion cancelada","idCliente = 1 e idAnimal = "+animal.id.toString())
            }
        }
    },
        modifier = Modifier
            .width(120.dp)
            .padding(top = 50.dp)) {

        if (!isButtonClicked) {
            Text(text = "Adoptar")
            } else Text(text = "Solicitado")
        }

    //Logica del like
    var islikeClicked by rememberSaveable  { mutableStateOf(false) }
    /* Inicializacion de los likes, si al animal ya se le ha dado like previamente, inicializa
    la variable islikeClicked para que pinte el corazon en consecuencia*/
    var result by remember { mutableStateOf<Favoritos?>(null) }
    LaunchedEffect(true) {
        GlobalScope.async(Dispatchers.IO) {
            //La peticion a la base de datos de forma asincrona
            //Inserta en la base de datos si sabe que no existe el id de dicho animal en la bd
            val query = GlobalScope.async(Dispatchers.IO) {
                room.favoritosDAO().getFavByIdAnimal(animal.id.toLong(),1.toLong())
            }
            result = query.await()
        }
    }
    if(result!=null){
        islikeClicked = true
    } else islikeClicked = false

    IconButton(onClick = {
        islikeClicked= !islikeClicked
        if (!islikeClicked) {
            //ha pulsado sobre no me gusta, luego lo elimina de la
            GlobalScope.launch {
                //La peticion a la base de datos de forma asincrona
                //Elimina de la base de la tabla favoritos, dicho animal
                room.favoritosDAO().deleteFav(Favoritos(1.toLong(),animal.id.toLong()))
                var favoritos: List<Favoritos> = room.favoritosDAO().getFavsByIdClient(1.toLong())

                Log.i("Numero de favs ", favoritos.size.toString())
            }
        }else{
            GlobalScope.launch {
                //La peticion a la base de datos de forma asincrona
                //Elimina de la base de la tabla favoritos, dicho animal
                room.favoritosDAO().setFav(Favoritos(1.toLong(),animal.id.toLong()))
                var favoritos: List<Favoritos> = room.favoritosDAO().getFavsByIdClient(1.toLong())

                Log.i("Numero de favs ", favoritos.size.toString())
            }
        }
    },
        modifier = Modifier
            .padding(top = 10.dp)){

        if (!islikeClicked) {
            Icon(Icons.Default.FavoriteBorder,
                contentDescription = "like button"
            )

        } else {
            Icon(Icons.Default.Favorite,
                contentDescription = "like button")
        }
    }
}

@Composable
fun Animal_Info(animal: Animal) {
    Column {
        Text(
            text = animal.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if(animal.description != null)
                        animal.description
                    else
                        "No hay descripción",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth(0.65f)
        )

        val caracteristicas = hashMapOf(
            "Edad" to animal.age,
            "Pelaje" to animal.coat,
            "Género" to animal.gender,
            "Tamaño" to animal.size,
            "Especie" to animal.species,
            "Tipo" to animal.type,
        )

        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
        ) {
            items(caracteristicas.toList()) { (key, value) ->
                if (value != null)
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