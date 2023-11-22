package com.example.proyecto_en_la_sombra.Model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Administrador (
    @PrimaryKey(autoGenerate = true)
    val idAdministrador: Long,
    val nombre: String
)

data class AdministradorInsertAnimales(
    @Embedded
    val administrador : Administrador,
    @Relation(
        parentColumn="idAdministrador",
        entityColumn="idAnimal"
    )
    val animals : List<Animal>
)

data class AdministradorAgregaOrganizaciones(
    @Embedded
    val administrador : Administrador,
    @Relation(
        parentColumn="idAdministrador",
        entityColumn="idProtectora"
    )
    val organizaciones : List<Protectora>
)

data class AdministradorBloquaClientes(
    @Embedded
    val administrador : Administrador,
    @Relation(
        parentColumn="idAdministrador",
        entityColumn="idCliente"
    )
    val clientes : List<Cliente>
)