package com.example.proyecto_en_la_sombra.api.model

data class RemoteModelPage(
    val animals: List<Animal>,
    val pagination: Pagination
)