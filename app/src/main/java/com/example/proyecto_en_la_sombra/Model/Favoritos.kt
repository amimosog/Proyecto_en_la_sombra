package com.example.proyecto_en_la_sombra.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys=["idCliente","idAnimal"])
data class Favoritos(
    val idCliente: Long, //Cliente que marca un animal como favorito
    val idAnimal: Long //Animal que es marcado como favorito
)
