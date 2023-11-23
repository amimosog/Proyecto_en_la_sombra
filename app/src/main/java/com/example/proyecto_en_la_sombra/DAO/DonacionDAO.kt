package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyecto_en_la_sombra.Model.*

@Dao
interface DonacionDAO {
    //Obtiene todas las donaciones
    @Query("SELECT * FROM Donacion")
    suspend fun getDonaciones() : List<Donacion>

    //Inserta una lista de donaciones
    @Insert
    suspend fun setDonaciones(donaciones : List<Donacion>)
}