package com.example.proyecto_en_la_sombra.api.TypeModel

data class Type(
    val _links: Links,
    val coats: List<String>,
    val colors: List<String>,
    val genders: List<String>,
    val name: String
)