
package com.example.proyecto_en_la_sombra.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_en_la_sombra.Model.Favoritos

@Dao
interface FavoritosDAO{
    //Obtener todos los favoritos de un usuario dado su id
    @Query("SELECT * FROM Favoritos WHERE idCliente = :id")
    suspend fun getFavsByIdClient(id:Long) : List<Favoritos>

    //Obtiene un favorito es especifico el id del animal favorito
    @Query("SELECT * FROM Favoritos WHERE idAnimal = :idAnimal AND idCliente = :idCliente")
    suspend fun getFavByIdAnimal(idAnimal:Long, idCliente: Long) : Favoritos

    //Inserta un favorito de un usuario dado el objeto Favorito
    @Insert
    suspend fun setFav(fav: Favoritos)

    //Elimina de favoritos un animal dado su id
    @Delete
    suspend fun deleteFav(fav:Favoritos)
}

