package com.example.proyecto_en_la_sombra


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.organizationsModel.Organization
import com.example.proyecto_en_la_sombra.navigation.AppNavigation
import kotlinx.coroutines.launch
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB

import com.example.proyecto_en_la_sombra.Model.*
import java.time.Duration
import java.util.concurrent.TimeUnit

private const val grant_type = "client_credentials"
private const val client_id = "jt78yfZFePKyGM8tmLpHZduPe4wiobusxA4gvGrxv5p9xlMREy"
private const val client_secret = "ADto7Ake8ThsPUe57IprbsLHORd29qhZynDdw1ej"
var auth = ""

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        val room: AplicacionDB = AplicacionDB.getInstance(context = this)

        //Si es la primera vez que ejecutas ROOM descometa este bloque de codigo para crear un cliente
        /*
        lifecycleScope.launch {
            var cliente : Cliente = Cliente(1, "Richar","Widmark", "rwidmark", "rwidmark69@gmail.com", "+34644501112","Agente internacional y de vacas bravas fiuuuuu", null)
            room.clienteDAO().insertCliente(cliente)
        }*/

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
        }
        setContent {
            AppNavigation(this)
        }
    }
}