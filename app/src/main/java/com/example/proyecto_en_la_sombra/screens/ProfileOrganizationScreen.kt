package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.R


@Composable
fun profileOrganization(navController: NavController) {
    Column(Modifier.padding(8.dp)) {
        Column {
            Row {
                UserImage()
                UserInfo()
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialMedia()
        }
        Column {
            DetailInfo()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            UserGallery()
            ReviewsField()
        }
        Column(modifier = Modifier.padding(5.dp)) {
            Reviews(1)
        }
    }
}

@Composable
fun UserImage() {
    Image(
        painterResource(id = R.drawable.ic_launcher_foreground),
        "UserImage",
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
            .background(color = Color.DarkGray)
    )
}

@Composable
fun UserInfo() {
    Column() {
        Text(
            "Nombre de la organización",
            modifier = Modifier.padding(top = 7.dp, start = 5.dp),
            fontSize = 20.sp
        )
        Text(
            "Ciudad",
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 14.sp
        )
        Text(
            "Pais",
            modifier = Modifier.padding(top = 1.dp, start = 5.dp),
            fontSize = 12.sp
        )
    }
}

@Composable
fun DetailInfo() {
    Text(
        "Email: ",
        modifier = Modifier.padding(top = 7.dp, start = 7.dp),
        fontSize = 14.sp
    )
    Text(
        "Dirección: ",
        modifier = Modifier.padding(top = 7.dp, start = 7.dp),
        fontSize = 14.sp
    )
    Text(
        "Número de teléfono: ",
        modifier = Modifier.padding(top = 7.dp, start = 7.dp),
        fontSize = 14.sp
    )
    Text(
        "Página web: ",
        modifier = Modifier.padding(top = 7.dp, start = 7.dp),
        fontSize = 14.sp
    )
}

@Composable
fun UserGallery() {
    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)) {
        items(10) {
            Image(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier
                    .clickable {}
                    .fillMaxSize()
                    .fillMaxWidth())
        }
    }
}

@Composable
fun SocialMedia() {
    val logoModifier = Modifier
        .padding(top = 5.dp, start = 10.dp)
        .size(60.dp)
        .clip(CircleShape)

    Row {
        Image(painterResource(id = R.drawable.twitter_logo), "twitter_logo", logoModifier)
        Image(painterResource(id = R.drawable.youtube_logo), "youtube_logo", logoModifier)
        Image(painterResource(id = R.drawable.facebook_logo), "facebook_logo", logoModifier)
        Image(
            painterResource(id = R.drawable.pinterest_logo),
            "pinterest_logo", logoModifier
        )
        Image(
            painterResource(id = R.drawable.instagram_logo), "instagram_logo", logoModifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsField() {
    var review by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
            .background(color = Color(165, 165, 165))
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¿Quieres dejar una opinión sobre esta organización?",
            fontSize = 14.sp
        )

        TextField(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .height(47.dp),
            value = review,
            onValueChange = { review = it },
            placeholder = {
                Text(
                    text = "¿Algo que decir?",
                    modifier = Modifier.alpha(0.5F),
                    fontSize = 12.sp
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { review = "" }
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
fun Reviews(id: Int) {
    Column(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(10.dp)),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(212, 212, 212))
        ) {
            Row {
                Column {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 7.dp, start = 7.dp)
                    )
                    Text(
                        text = "Nombre de usuario",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp, top = 3.dp, bottom = 4.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Esto es una review",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center,
                        softWrap = true,
                    )
                }
            }
        }
    }
}