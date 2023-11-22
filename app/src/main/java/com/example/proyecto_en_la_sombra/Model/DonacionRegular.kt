package com.example.proyecto_en_la_sombra.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys=["idCliente","idAnimal"])
data class DonacionRegular(
    @PrimaryKey(autoGenerate = true)
    val idCliente: Long,
    val idAnimal: Long,
    val fecha: String,
    val cantidad: Float
)
