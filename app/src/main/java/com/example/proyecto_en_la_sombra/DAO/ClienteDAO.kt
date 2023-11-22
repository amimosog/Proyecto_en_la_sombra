package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.*

@Dao
interface ClienteDAO {
    //Obtiene una lista de los animales favoritos de todos los clientes
    @Query("SELECT * FROM Cliente")
    fun getfavoritosDeClientes() : List<ClienteTieneFavoritosAnimales>

    //Obtiene una lista de las donaciones de todos los clientes
    @Query("SELECT * FROM Cliente")
    fun getdonacionesDeClientes() : List<ClienteDonaProtectoras>

    //Devuelve una lista de las valoraciones realizadas por todos los clientes
    @Query("SELECT * FROM Cliente")
    fun getValoracionesDeClientes() : List<ClienteValoraProtectoras>

    //Devuelve una lista de los clientes que han apadrinado animales junto a dicho animales
    @Query("SELECT * FROM Cliente")
    fun getClienteApadrinanAnimales() : List<ClienteApadrinaAnimales>

    //Devuelve una lista de los clientes que solicitaron adopciones junto a los animales que solicitaron adoptar
    @Query("SELECT * FROM Cliente")
    fun getClienteSolicitoAdopciones() : List<ClienteSolicitaAdoptar>
}