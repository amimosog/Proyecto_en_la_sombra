package com.example.proyecto_en_la_sombra.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContactPhone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.Model.Cliente
import com.example.proyecto_en_la_sombra.R
import com.example.proyecto_en_la_sombra.Repository.AplicacionDB
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*import com.example.proyecto_en_la_sombra.screens.ui.theme.Proyecto_en_la_sombraTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_en_la_sombraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}*/

@Composable
fun RegisterActivity(navController: NavController, context: Context){
    RegisterComponents(navController, context)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterComponents(navController: NavController, context:Context) {

    val room : AplicacionDB = AplicacionDB.getInstance(context)

    var name: String by remember { mutableStateOf("") }
    var apellidos: String by remember { mutableStateOf("") }
    var phoneNumber: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var pass: String by remember { mutableStateOf("") }
    var passCheck: String by remember { mutableStateOf("") }
    var passwordVisible: Boolean by remember { mutableStateOf(false) }
    var passwordCheckVisible: Boolean by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_register),
                contentScale = ContentScale.FillBounds
            ), contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.background_register),
            contentDescription = "register_background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.95F)
                .fillMaxWidth(0.85F)
                .padding(top = 30.dp)
                .offset(y = 30.dp)
                .background(Color.White, RoundedCornerShape(8.dp))

        ) {
            item {
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "Crear cuenta",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.71F)
                )
                Spacer(Modifier.height(60.dp))
                TextField(
                    shape = RoundedCornerShape(8.dp),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = "Nombre", modifier = Modifier.alpha(0.5F)) },
                    trailingIcon = {
                        Icon(Icons.Outlined.Person, contentDescription = "name_icon")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = apellidos,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { apellidos = it },
                    placeholder = { Text(text = "Apellidos", modifier = Modifier.alpha(0.5F)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))

                TextField(
                    value = phoneNumber,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { phoneNumber = it },
                    placeholder = {
                        Text(
                            text = "Número de telefono",
                            modifier = Modifier.alpha(0.5F)
                        )
                    },
                    trailingIcon = {
                        Icon(Icons.Outlined.ContactPhone, contentDescription = "email_icon")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = email,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { email = it },
                    placeholder = { Text(text = "Email", modifier = Modifier.alpha(0.5F)) },
                    trailingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = "email_icon")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = pass,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { pass = it },
                    placeholder = { Text(text = "Contraseña", modifier = Modifier.alpha(0.5F)) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            if (passwordVisible) {
                                Icon(
                                    Icons.Outlined.Visibility,
                                    contentDescription = "pass_visibility_on"
                                )
                            } else Icon(
                                Icons.Outlined.VisibilityOff,
                                contentDescription = "pass_visibility_off"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = passCheck,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { passCheck = it },
                    placeholder = {
                        Text(
                            text = "Confirme contraseña",
                            modifier = Modifier.alpha(0.5F)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordCheckVisible = !passwordCheckVisible }) {
                            if (passwordCheckVisible) {
                                Icon(
                                    Icons.Outlined.Visibility,
                                    contentDescription = "pass_visibility_on"
                                )
                            } else Icon(
                                Icons.Outlined.VisibilityOff,
                                contentDescription = "pass_visibility_off"
                            )
                        }
                    },
                    visualTransformation = if (passwordCheckVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.95F)
                )
                Spacer(Modifier.height(35.dp))
                Button(onClick = {
                    //Comprobar que todos los campos estan rellenos
                    if (name.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && passCheck.isNotEmpty()) {
                        if (pass.equals(passCheck)) {
                            //Introducir los datos en la base de datos y navegar al login
                            GlobalScope.launch {
                                var cliente = Cliente(
                                    name,
                                    apellidos,
                                    email.substringBefore('@'),
                                    email,
                                    pass,
                                    phoneNumber,
                                    null,
                                    null
                                )
                                //La peticion a la base de datos de forma asincrona
                                room.clienteDAO().insertCliente(cliente)

                                Log.i("Insercion usuario", "usuario insertado")
                            }

                            navController.navigate(route = AppScreens.LoginActivity.route)
                        }
                    }
                }) {
                    Text(text = "Registrar")
                }
                Button(onClick = {
                    navController.navigate(route = AppScreens.LoginActivity.route)
                }) {
                    Text(text = "Ya tengo cuenta")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RegisterPreview() {
//    RegisterComponents()
//}