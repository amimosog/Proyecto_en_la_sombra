package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.Model.Favoritos
import com.example.proyecto_en_la_sombra.Model.SolicitudAdopcion
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.Repository.animalRepository
import com.example.proyecto_en_la_sombra.Repository.clientRepository
import com.example.proyecto_en_la_sombra.Repository.favoritosRepository
import com.example.proyecto_en_la_sombra.Repository.solicitudAdopciónRepository
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.RemoteResult
import com.example.proyecto_en_la_sombra.auth
import com.example.proyecto_en_la_sombra.emailActual
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


data class Photo(val image: Int)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileComponents(
    navController: NavController,
    users: clientRepository,
    solicitudAdopcionRepository: solicitudAdopciónRepository,
    favoritosRepository: favoritosRepository,
    animalRepository: animalRepository
) {
    var cliente: Cliente
    //Llamar a la base de datos para que cargue el nombre
    runBlocking {
        cliente = users.getClienteByEmail(emailActual)
    }

    //Obtenemos la lista de favoritos del usuario
    var resultFavs by remember { mutableStateOf<List<Favoritos>?>(null) }
    LaunchedEffect(true) {
        val query1 =
            GlobalScope.async(Dispatchers.IO) { favoritosRepository.getFavsByIdClient(users.getClienteByEmail(emailActual).idCliente) }
        resultFavs = query1.await()
    }

    var resultAdop by remember { mutableStateOf<List<SolicitudAdopcion>?>(null) }
    LaunchedEffect(true) {
        val query2 = GlobalScope.async(Dispatchers.IO) {
            solicitudAdopcionRepository.getAdopByIdClient(users.getClienteByEmail(emailActual).idCliente)
        }
        resultAdop = query2.await()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Row {
            ImagenUser()
            BloqueNombre(cliente)
        }

        BloqueDatos(cliente)

        //-------------------------TABS-------------------------------------------------
        val pagerState = rememberPagerState(
            pageCount = { 2 }
        )
        val coroutineScope = rememberCoroutineScope()
        TabRow(selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            divider = { },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 5.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                text = { Text("Favoritos") },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
            Tab(
                selected = pagerState.currentPage == 1,
                text = { Text("Adoptados") },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true
        ) { page ->
            if (page == 0) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    content = {
                        resultFavs?.forEachIndexed { index, fav ->
                            item {
                                ImagenFav(fav, navController, animalRepository)
                            }
                        }
                    }
                )
            } else{
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    content = {
                        resultAdop?.forEachIndexed { index, adop ->
                            item {
                                ImagenAdop(adop, navController, animalRepository)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ImagenUser() {
    Image(
        painterResource(R.drawable.ic_launcher_foreground),
        "Imagen del usuario",
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun ImagenFav(
    favorito: Favoritos,
    navController: NavController,
    animalRepository: animalRepository
) {

    var result by remember { mutableStateOf<RemoteResult?>(null) }
    LaunchedEffect(true) {
        val query = GlobalScope.async(Dispatchers.IO) {
            animalRepository.getAnimalById(favorito.idAnimal.toString())
        }
        result = query.await()
    }
    if (result?.animal?.photos?.isNotEmpty() == true) {
        AsyncImage(model = result!!.animal.photos[0].small,
            contentDescription = "",
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + result?.animal!!.id)
                })
    }
}

@Composable
fun ImagenAdop(
    solicitudAdopcion: SolicitudAdopcion,
    navController: NavController,
    animalRepository: animalRepository
) {
    var result by remember { mutableStateOf<RemoteResult?>(null) }
    LaunchedEffect(true) {
        val query = GlobalScope.async(Dispatchers.IO) {
            animalRepository.getAnimalById(solicitudAdopcion.idAnimal.toString())
        }
        result = query.await()
    }
    if (result?.animal?.photos?.isNotEmpty() == true) {
        AsyncImage(model = result!!.animal.photos[0].small,
            contentDescription = "",
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    navController.navigate(route = AppScreens.AnimalDetailScreen.route + "/" + result?.animal!!.id)
                })
    }
}

@Composable
fun BloqueNombre(cliente: Cliente) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(start = 8.dp)
        .clickable {
            expanded = !expanded
        }) {
        TextoNombre(
            cliente.nombre,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextoNombre(
            cliente.appellidos,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        cliente.descripcion?.let { //Este bloque se pone ya que si es nulo no crea el texto
            TextoNombre(
                it,
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.typography.bodyMedium,
                if (expanded) Int.MAX_VALUE else 1
            )
        }
    }
}

@Composable
fun BloqueDatos(cliente: Cliente) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Row {
            Text(
                "Nickname:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                cliente.nickname,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        }

        Row {
            Text(
                "Email:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                cliente.email,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        }

        Row {
            Text(
                "Num. tlf.:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            cliente.numTelefono?.let { //Bloque necesario ya que si es nulo el campo, no crea el Text
                Text(
                    it,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun TextoNombre(text: String, color: Color, style: TextStyle, lines: Int = Int.MAX_VALUE) {
    Text(text, color = color, style = style, maxLines = lines)
}
