package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyecto_en_la_sombra.Model.*

@Dao
interface AdministradoDAO {

    //Devuelve una lista de los administradores que bloquearon clientes junto a dichos clientes
    @Transaction
    @Query("SELECT * FROM Administrador")
    suspend fun getAdministradoresBloquearonClientes() : List<AdministradorBloquaClientes>

    //Devuelve una lista de los administradore que insertaron animales junto a dichos animales
    @Transaction
    @Query("SELECT * FROM Administrador")
    suspend fun getAdministradoresInsertaronAnimales() : List<AdministradorInsertAnimales>

    //Devuelve una lista de los administradores que agregaron organizaciones junto a dichas organizaciones
    @Transaction
    @Query("SELECT * FROM Administrador")
    suspend fun getAdministradoresAgregaronOrganizaciones() : List<AdministradorAgregaOrganizaciones>

    //Inserta un administrador en la base de datos
    @Insert
    suspend fun setAdministrador(administrador: Administrador)

}