package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.Donacion
import com.example.proyecto_en_la_sombra.Model.SolicitudAdopcion

@Dao
interface SolicitudAdopcionDAO {

    //Insertar una solicitud
    @Query("INSERT INTO SolicitudAdopcion (idCliente, idAnimal) VALUES (:idCliente,:idAnimal)")
    suspend fun setSolicitud(idCliente: Long, idAnimal: Long)

    //Insertar una solicitud completa con fecha
    @Query("INSERT INTO SolicitudAdopcion (idCliente, idAnimal, fecha) VALUES (:idCliente, :idAnimal, :fecha)")
    suspend fun setSolicitudCompleta(idCliente: Long, idAnimal: Long, fecha: String)

    //Obtener una solicitud por el id del animal y del cliente
    @Query("SELECT * FROM SolicitudAdopcion WHERE idCliente = :idCliente AND idAnimal = :idAnimal")
    suspend fun getSolicitud(idCliente: Long, idAnimal: Long) : SolicitudAdopcion

    //Obtener un animal ya solicitado para adoptar por su id
    @Query("SELECT * FROM SolicitudAdopcion WHERE idAnimal = :idAnimal")
    suspend fun getAnimalAdop(idAnimal: Long) : SolicitudAdopcion

    //Elimina una solicitud de adopcion
    @Query("DELETE FROM SolicitudAdopcion WHERE idCliente = :idCliente AND idAnimal = :idAnimal")
    suspend fun removeSolicitud(idCliente: Long, idAnimal: Long)

    //Obtener todas las adopciones de un usuario dado su id
    @Query("SELECT * FROM SolicitudAdopcion WHERE idCliente = :id")
    suspend fun getAdopByIdClient(id:Long) : List<SolicitudAdopcion>
}