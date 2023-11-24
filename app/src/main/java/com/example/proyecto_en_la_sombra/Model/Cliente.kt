package com.example.proyecto_en_la_sombra.Model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Cliente (
    @PrimaryKey(autoGenerate = true)
    val idCliente: Long,
    val nombre: String,
    val descripcion: String?,
    val bloqueador: Long? //id del administrador que lo bloqueo si se ha dado el caso
)

data class ClienteSolicitaAdoptar(
    @Embedded
    val cliente: Cliente,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idAnimal"
    )
    val animales : List<Animal>
)
/*
data class ClienteTieneFavoritosAnimales(
    @Embedded
    val cliente: Cliente,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idAnimal",
        associateBy = Junction(Favoritos::class)
    )
    val animales : List<Animal>
)

data class ClienteDonaProtectoras(
    @Embedded
    val cliente: Cliente,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idProtectora",
        associateBy = Junction(Donacion::class)
    )
    val protectora : List<Protectora>
)

data class ClienteValoraProtectoras(
    @Embedded
    val cliente: Cliente,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idProtectora",
        associateBy = Junction(Valoracion::class)
    )
    val protectora : List<Protectora>
)

data class ClienteApadrinaAnimales(
    @Embedded
    val cliente: Cliente,
    @Relation(
        parentColumn="idCliente",
        entityColumn="idAnimal",
        associateBy = Junction(DonacionRegular::class)
    )
    val animales : List<Animal>
)*/