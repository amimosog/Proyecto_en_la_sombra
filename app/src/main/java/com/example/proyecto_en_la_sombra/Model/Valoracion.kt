package com.example.proyecto_en_la_sombra.Model

import androidx.room.Entity

@Entity(primaryKeys=["idCliente","idProtectora"])
data class Valoracion(
    val idCliente: Long, //identificador del cliente que realiza la valoracion
    val idProtectora: Long, //identificador de la protectora a la que realizan la valoracion
    val valoracion: String,
    val puntuacion: Int
)
