package com.example.proyecto_en_la_sombra.api.authModel

data class authModel(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)