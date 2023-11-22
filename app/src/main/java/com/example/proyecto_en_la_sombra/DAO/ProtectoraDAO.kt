/*
package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.ProtectoraAnunciaAnimales
import com.example.proyecto_en_la_sombra.Model.ProtectoraEsDonadaPorClientes
import com.example.proyecto_en_la_sombra.Model.ProtectoraEsValoradaPorClientes

@Dao
interface ProtectoraDAO {
    //Devuelve una lista de las protectoras que han sido donadas por clientes junto a dicho clientes
    @Query("SELECT * FROM Protectora")
    fun getProtectorasDonadasPorClientes() : List<ProtectoraEsDonadaPorClientes>

    //Devuelve una lista de las protectoras que han sido valoradas por clientes junto a dichos clientes
    @Query("SELECT * FROM Protectora")
    fun getProtectorasValoradasPorClientes() : List<ProtectoraEsValoradaPorClientes>

    //Devuelve una lista de las protectoras que anunciaron animales junto con dichos animales
    @Query("SELECT * FROM Protectora")
    fun getOrganizacionesAnunciaronAnimales() : List<ProtectoraAnunciaAnimales>
}
 */