package com.example.proyecto_en_la_sombra


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.navigation.AppNavigation
import kotlinx.coroutines.launch

private const val auth = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqdDc4eWZaRmVQS3lHTTh0bUxwSFpkdVBlNHdpb2J1c3hBNGd2R3J4djVwOXhsTVJFeSIsImp0aSI6IjQ4M2ZmNzc3MWE0Nzk0MDFhYmY5MzdmMmMwOTZlZTZiODhkNWU0NDlkZWM2MGVjMjA1NjFmM2ZiZTU2YWY1OGNiNjM1NjZkMzhiZWUzZDI5IiwiaWF0IjoxNzAwNjUwNTcwLCJuYmYiOjE3MDA2NTA1NzAsImV4cCI6MTcwMDY1NDE3MCwic3ViIjoiIiwic2NvcGVzIjpbXX0.I8IFjtKXGzNTGwEz6xSIeaUJX94674HTHRfUTJEsIl-B3igt1nFzhcKQECm5tYZjP8fHtTb4lVHS-OXp7MK7Qv9la_k86HcxJC7KLCELM5_uEAPqsZbTvqUF5whgwgixXCMM5NApk9bfhmREQTbyJ1xUpsN_aXVARRP1kk_jDlyk_q5gkB5_kUOzhrEQIsScqGQpHcjrFy9DHFQ2JG2umXzyoZ0Sr8p8Nm8CF4Z3onlwCqJKYRJCj8TDwI2puSenSR0DBaSZVryOOgPK-lRQi8HHkvHmjNHx3i30Ad8b1E1UxFclg3vv47LKwsd3PzWofOuUAABWf9Om2z6HpICiNQ"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        lifecycleScope.launch {
            val animal = service.getAnimals("69771579")
            println(animal)
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