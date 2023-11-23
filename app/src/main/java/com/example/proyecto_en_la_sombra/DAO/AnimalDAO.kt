package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.Animal
//import com.example.proyecto_en_la_sombra.Model.animalEsApadrinadoPorClientes

@Dao
interface AnimalDAO {
    //Devuelve una lista de los animales que han sido apadrinados junto a los clientes que los han apadrinado
    //@Query("SELECT * FROM Animal")
    //suspend fun getAnimalesApadrinadosPorClientes() : List<animalEsApadrinadoPorClientes>

    //Inserta un animal
    @Insert
    suspend fun insertAnimal(animal: Animal)

    //Inserta una lista de clientes que han apadrinado un determinado animal
    //@Insert
    //suspend fun insertAnimalesApadrinadosPorClientes(animalEsApadrinadoPorClientes: animalEsApadrinadoPorClientes)
}