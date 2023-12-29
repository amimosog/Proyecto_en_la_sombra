package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Update
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.Collections


/*class SearchScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = listOf<String>("test1", "test2", "test3")
            SearchBarCustom()
            SelectCategory(name = "test", list = list)
        }
    }
}*/

val size = listOf<String>("", "small", "medium", "large", "xlarge")
val gender = listOf<String>("", "male", "female")
val age = listOf<String>("", "baby", "young", "adult")
val type = listOf<String>("", "baby", "young", "adult")

var caracteristicas = hashMapOf(
    "size" to size,
    "gender" to gender,
    "age" to age,
    "type" to type
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCustom(navController: NavController) {
    var search by remember { mutableStateOf("") }
    var typeDropdown by remember { mutableStateOf("") }
    var genderDropdown by remember { mutableStateOf("") }
    var ageDropdown by remember { mutableStateOf("") }
    var sizeDropdown by remember { mutableStateOf("") }



    Column (Modifier.fillMaxSize()) {
        Column {
            TextField(
                value = search,
                shape = RoundedCornerShape(8.dp),
                onValueChange = { search = it },
                placeholder = { Text(text = "Buscar", modifier = Modifier.alpha(0.5F)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    route = AppScreens.SearchResultsScreen.route + "?name=" + search + "&type=" + typeDropdown +
                                            "&size=" + sizeDropdown + "&gender=" + genderDropdown + "&age=" + ageDropdown
                                )
                            }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            typeDropdown = "Dog"
            getType(onUpdate = { caracteristicas = it })
            Column {
                dropdown(name = "age", list = caracteristicas.getValue("age")) { ageDropdown = it }
                dropdown(name = "type", list = caracteristicas.getValue("type")) {
                    typeDropdown = it
                }
                dropdown(name = "gender", list = caracteristicas.getValue("gender")) {
                    genderDropdown = it
                }
                dropdown(name = "size", list = caracteristicas.getValue("size")) {
                    sizeDropdown = it
                }
                //dropdown(name = name, list = list, onTextChange = { sizeDropdown = it })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropdown(name: String, list: List<String>, onUpdate: (String) -> Unit) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    var selectedType: String by remember { mutableStateOf("") }
    var lista = list
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { newValue ->
            expanded = newValue
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        TextField(
            readOnly = true,
            value = selectedType,
            onValueChange = { selectedType = it },
            label = { Text(name) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            lista.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selectedType = it
                        onUpdate(it)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }
    }
}

@Composable
fun getType(onUpdate: (HashMap<String, List<String>>) -> Unit) {
    val composed = remember { false }
    LaunchedEffect(true) {
        if (!composed) {
            val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
            var query = GlobalScope.async(Dispatchers.IO) { service.getSearchTypes(auth) }
            var result = query.await()!!
            var tiposanimal: List<String> = listOf()
            for (i in result.types) {
                tiposanimal = tiposanimal.plus(i.name)
                println(i.name)
            }
            caracteristicas.put("type", tiposanimal)

            for (i in caracteristicas) {
                println(i.key)
                println(i.value)
            }
        }
    }
}

//Solo actualiza la primer vez que se llama a la funcion
/*
@Composable
fun updateColors(type : String){
    println("He pasado por aquí")
    dropdownOptions.clear()
    LaunchedEffect(true) {
            val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
            var query = GlobalScope.async(Dispatchers.IO) { service.getSearchTypebyAnimal(auth, "Dog") }
            var result = query.await()!!
            println(result.type.colors)
            for (i in result.type.colors) {
                dropdownOptions.add(i)
            }
    }
}
*/
@Composable
@Preview
fun Preview() {
    //SearchBarCustom(null)
}