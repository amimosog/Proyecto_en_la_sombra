package com.example.proyecto_en_la_sombra.Model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Protectora(
    @PrimaryKey(autoGenerate = true)
    val idProtectora: Long,
    val nombre: String,
    val descripcion: String
)

data class ProtectoraAnunciaAnimales(
    @Embedded
    val protectora: Protectora,
    @Relation(
        parentColumn="idProtectora",
        entityColumn="idAnimal"
    )
    val animals : List<Animal>
)

data class ProtectoraEsDonadaPorClientes(
    @Embedded
    val protectora: Protectora,
    @Relation(
        parentColumn="idProtectora",
        entityColumn="idCliente",
        associateBy = Junction(Donacion::class)
    )
    val clientes : List<Cliente>
)

data class ProtectoraEsValoradaPorClientes(
    @Embedded
    val protectora: Protectora,
    @Relation(
        parentColumn="idProtectora",
        entityColumn="idCliente",
        associateBy = Junction(Valoracion::class)
    )
    val clientes : List<Cliente>
)