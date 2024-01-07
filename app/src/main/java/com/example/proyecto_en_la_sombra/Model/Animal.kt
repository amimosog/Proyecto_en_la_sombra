package com.example.proyecto_en_la_sombra.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal (
    val nombre: String,
    val descripcion: String?,
    val protectora: String, //id de la protectora del animal
    val fotos: String? //url con las imagenes del animal TODO:Hacer que sea una lista o algo asi
){
    @PrimaryKey(autoGenerate = true)
    var idAnimal: Long = 0
}
