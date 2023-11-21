@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto_en_la_sombra


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyecto_en_la_sombra.navigation.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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