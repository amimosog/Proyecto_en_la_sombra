package com.example.proyecto_en_la_sombra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_en_la_sombra.ui.theme.Proyecto_en_la_sombraTheme
import kotlinx.coroutines.launch

private val photos: List<Photo> = listOf(
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
    Photo(R.drawable.ic_launcher_foreground),
)

@ExperimentalFoundationApi
class ProfileUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_en_la_sombraTheme() {
                MyComponent()
            }
        }
    }
}

data class Photo(val image: Int)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyComponent() {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(8.dp)) {
        Row{
            ImagenUser()
            BloqueNombre()
        }

        BloqueDatos()

        //-------------------------TABS-------------------------------------------------
        val pagerState = rememberPagerState(
            pageCount = {2}
        )
        val coroutineScope = rememberCoroutineScope()
        TabRow(selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            divider = {  },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 5.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        ){
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
                text = { Text("Preferencias") },
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
                            photos.forEachIndexed { index, photo ->
                                item {
                                    ImagenFav(photo)
                                }
                            }
                        }
                    )
            }else{
                BloqueDatos()
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
fun ImagenFav(photo: Photo) {
    Image(
        painterResource(photo.image),
        "Imagen del animal",
        modifier = Modifier
            .size(80.dp)
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun BloqueNombre() {
    var expanded by remember { mutableStateOf(false)}

    Column(modifier = Modifier
        .padding(start = 8.dp)
        .clickable {
            expanded = !expanded
        }) {
        TextoNombre(
            "Alejandro",
            MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextoNombre(
            "Mimoso García",
            MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextoNombre(
            "Dueño de 3 mascotas, adoro los gatos y mis " +
                    "perros son como de mi familia. Siempre dispuesto " +
                    "a encontrar un nuevo amigo al que ayudar.",
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.typography.bodyMedium,
            if (expanded) Int.MAX_VALUE else 1
        )
    }
}

@Composable
fun BloqueDatos() {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Row {
            Text("Nickname:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge)
            Text("AlexMiGar",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp))
        }

        Row {
            Text("Email:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge)
            Text("amg@gmail.com",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp))
        }

        Row {
            Text("Num. tlf.:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge)
            Text("987654321",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun TextoNombre(text: String, color: Color, style: TextStyle, lines: Int = Int.MAX_VALUE){
    Text(text, color = color, style = style, maxLines = lines)
}

@Preview(showSystemUi = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewComponent() {
    Proyecto_en_la_sombraTheme {
        MyComponent()
    }
}