package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.animalEsApadrinadoPorClientes

@Dao
interface AnimalDAO {
    //Devuelve una lista de los animales que han sido apadrinados junto a los clientes que los han apadrinado
    @Query("SELECT * FROM Animal")
    fun getAnimalesApadrinadosPorClientes() : List<animalEsApadrinadoPorClientes>
}