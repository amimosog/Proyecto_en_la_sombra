package com.example.proyecto_en_la_sombra.api.organizationsModel

data class Organization(
    val _links: Links,
    val address: Address,
    val adoption: Adoption,
    val distance: Any,
    val email: String,
    val hours: Hours,
    val id: String,
    val mission_statement: Any,
    val name: String,
    val phone: String,
    val photos: List<Photo>,
    val social_media: SocialMedia,
    val url: String,
    val website: Any
)