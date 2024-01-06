package com.example.proyecto_en_la_sombra.Model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Protectora(
    val nombre: String,
    val numTlf: String,
    val email: String,
    val ciudad: String,
    val pais: String,
    val logo: String?
){
    @PrimaryKey(autoGenerate = true)
    var idProtectora: Long = 0
}

data class ProtectoraAnunciaAnimales(
    @Embedded
    val protectora: Protectora,
    @Relation(
        parentColumn="idProtectora",
        entityColumn="idAnimal"
    )
    val animals : List<Animal>
)