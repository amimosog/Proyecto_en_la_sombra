package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyecto_en_la_sombra.Model.*

@Dao
interface ClienteDAO {
    //Obtiene una lista de los animales favoritos de todos los clientes
    //@Query("SELECT * FROM Cliente")
    //suspend fun getfavoritosDeClientes() : List<ClienteTieneFavoritosAnimales>

    //Obtiene una lista de las donaciones de todos los clientes
    //@Query("SELECT * FROM Cliente")
    //suspend fun getdonacionesDeClientes() : List<ClienteDonaProtectoras>

    //Devuelve una lista de las valoraciones realizadas por todos los clientes
    //@Query("SELECT * FROM Cliente")
    //suspend fun getValoracionesDeClientes() : List<ClienteValoraProtectoras>

    //Devuelve una lista de los clientes que han apadrinado animales junto a dicho animales
    //@Query("SELECT * FROM Cliente")
    //suspend fun getClienteApadrinanAnimales() : List<ClienteApadrinaAnimales>

    //Devuelve una lista de los clientes que solicitaron adopciones junto a los animales que solicitaron adoptar
    @Transaction
    @Query("SELECT * FROM Cliente")
    suspend fun getClienteSolicitoAdopciones(): List<ClienteSolicitaAdoptar>

    @Query("SELECT * FROM Cliente")
    suspend fun getClientes(): List<Cliente>

    //Get a client by a given id
    @Query("SELECT * FROM Cliente WHERE idCliente = :id")
    suspend fun getClientById(id: Int): Cliente

    @Query("SELECT * FROM Cliente WHERE email = :email")
    suspend fun getClienteByEmail(email : String) : Cliente

    //Inserta un Cliente
    @Insert
    suspend fun insertCliente(cliente: Cliente)

}