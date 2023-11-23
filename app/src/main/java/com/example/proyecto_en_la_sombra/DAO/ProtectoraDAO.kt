package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyecto_en_la_sombra.Model.ProtectoraAnunciaAnimales
//import com.example.proyecto_en_la_sombra.Model.ProtectoraEsDonadaPorClientes
//import com.example.proyecto_en_la_sombra.Model.ProtectoraEsValoradaPorClientes

@Dao
interface ProtectoraDAO {
    //Devuelve una lista de las protectoras que han sido donadas por clientes junto a dicho clientes
    //@Query("SELECT * FROM Protectora")
    //suspend fun getProtectorasDonadasPorClientes() : List<ProtectoraEsDonadaPorClientes>

    //Devuelve una lista de las protectoras que han sido valoradas por clientes junto a dichos clientes
    //@Query("SELECT * FROM Protectora")
    //suspend fun getProtectorasValoradasPorClientes() : List<ProtectoraEsValoradaPorClientes>

    //Devuelve una lista de las protectoras que anunciaron animales junto con dichos animales
    @Transaction
    @Query("SELECT * FROM Protectora")
    suspend fun getOrganizacionesAnunciaronAnimales() : List<ProtectoraAnunciaAnimales>

}