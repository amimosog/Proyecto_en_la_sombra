package com.example.proyecto_en_la_sombra.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_en_la_sombra.navigation.AppScreens
import androidx.compose.runtime.getValue
import androidx.room.util.dropFtsSyncTriggers
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.auth
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


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


val size = listOf<String>("","small", "medium", "large", "extra-large")
val gender = listOf<String>("","male", "female")
val age = listOf<String>("","baby", "young", "adult")

var caracteristicas = hashMapOf(
    "size" to size,
    "gender" to gender,
    "age" to age
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCustom(navController: NavController) {
    var search by remember { mutableStateOf("") }
    var typeDropdown by remember { mutableStateOf("") }
    var genderDropdown by remember { mutableStateOf("") }
    var ageDropdown by remember { mutableStateOf("") }
    var sizeDropdown by remember { mutableStateOf("") }


    TextField(
        value = search,
        onValueChange = { search = it },
        placeholder = { Text(text = "Buscar", modifier = Modifier.alpha(0.5F)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navController.navigate(route = AppScreens.SearchResultsScreen.route + "?name=" + search + "&type="+ typeDropdown +
                                "&size=" + sizeDropdown + "&gender="+ genderDropdown +"&age=" + ageDropdown)
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )

    getType(onUpdate = { caracteristicas = it })
    for (i in caracteristicas) {
        println(i.key)
        println(i.value)
    }
    Column {
        dropdown(name = "age", list = caracteristicas.getValue("age"), onUpdate = { ageDropdown = it })
        //dropdown(name = "Type", list = caracteristicas.getValue("type"), onUpdate = { typeDropdown = it })
        //dropdown(name = "gender", list = list2, onTextChange = { ageDropdown = it })
        //dropdown(name = name, list = list, onTextChange = { sizeDropdown = it })
        //dropdown(name = "gender", list = list2, onTextChange = { genderDropdown = it })
        //dropdown(name = name, list = list, onTextChange = { sizeDropdown = it })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropdown(name: String, list: List<String>, onUpdate: (String) -> Unit){
    var expanded : Boolean by remember { mutableStateOf(false)}
    var selectedType : String by remember { mutableStateOf("")}
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
            onValueChange = { },
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
            list.forEach {
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
fun getType(onUpdate: (HashMap<String,List<String>>) -> Unit){
    LaunchedEffect(true) {
        val service = RetrofitService.RetrofitServiceFactory.makeRetrofitService()
        var query = GlobalScope.async(Dispatchers.IO) { service.getSearchTypes(auth) }
        var result = query.await()!!
        val tiposanimal : List<String> = listOf()
        for (i in result.types) {
            tiposanimal.plus(i.name)
            println(i.name)
        }
        caracteristicas["type"] = tiposanimal
        onUpdate(caracteristicas)
    }
}

@Composable
@Preview
fun Preview (){
 //   SearchBarCustom()
}