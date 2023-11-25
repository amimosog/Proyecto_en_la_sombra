package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.SolicitudAdopcion

@Dao
interface SolicitudAdopcionDAO {

    //Insertar una solicitud
    @Query("INSERT INTO SolicitudAdopcion (idCliente, idAnimal) VALUES (:idCliente,:idAnimal)")
    suspend fun setSolicitud(idCliente: Long, idAnimal: Long)

    //Obtener una solicitud por el id del animal y del cliente
    @Query("SELECT * FROM SolicitudAdopcion WHERE idCliente = :idCliente AND idAnimal = :idAnimal")
    suspend fun getSolicitud(idCliente: Long, idAnimal: Long) : SolicitudAdopcion

    //Elimina una solicitud de adopcion
    @Query("DELETE FROM SolicitudAdopcion WHERE idCliente = :idCliente AND idAnimal = :idAnimal")
    suspend fun removeSolicitud(idCliente: Long, idAnimal: Long)
}