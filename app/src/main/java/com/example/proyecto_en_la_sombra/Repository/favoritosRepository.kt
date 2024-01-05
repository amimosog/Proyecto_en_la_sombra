package com.example.proyecto_en_la_sombra.Repository

import com.example.proyecto_en_la_sombra.Model.Favoritos

class favoritosRepository(instance: AplicacionDB) {
    var store = instance

    suspend fun getFavsByIdClient(id: Long): List<Favoritos> =
        store.favoritosDAO().getFavsByIdClient(id)

    suspend fun getFavByIdAnimal(idAnimal: Long, idCliente: Long): Favoritos =
        store.favoritosDAO().getFavByIdAnimal(idAnimal, idAnimal)

    suspend fun setFav(fav: Favoritos) = store.favoritosDAO().setFav(fav)

    suspend fun deleteFav(fav: Favoritos) = store.favoritosDAO().deleteFav(fav)
}