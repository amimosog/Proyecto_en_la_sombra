package com.example.proyecto_en_la_sombra.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys=["idCliente","idProtectora"])
data class Donacion(
    val idCliente: Long, //identificador del cliente que realiza la donacion
    val idProtectora: String, //identificador de la protectora a la que se realiza una donacion
    var cantidad: Float
)
