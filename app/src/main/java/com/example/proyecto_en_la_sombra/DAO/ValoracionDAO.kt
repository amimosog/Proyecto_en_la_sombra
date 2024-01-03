package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.Valoracion

@Dao
interface ValoracionDAO{
    @Query("INSERT INTO Valoracion (idCliente, idProtectora, valoracion) VALUES (:idCliente,:idProtectora,:valoracion)")
    suspend fun setValoracion(idCliente: Long, idProtectora: String, valoracion: String)

    @Query("SELECT * FROM Valoracion WHERE idProtectora = :idProtectora")
    suspend fun getValoracionByIdProtectora(idProtectora: String) : Valoracion
}
