package com.example.proyecto_en_la_sombra.Model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Animal (
    @PrimaryKey(autoGenerate = true)
    val idAnimal: Long,
    val nombre: String,
    val descripcion: String,
    val insertado: Long, //id del administrador que lo inserto
    val protectora: Long //id de la protectora del animal
)

data class animalEsApadrinadoPorClientes(
    @Embedded
    val anima : Animal,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idCliente",
        associateBy = Junction(DonacionRegular::class)
    )
    val clientes : List<Cliente>
)