package com.example.proyecto_en_la_sombra.api.model

data class Animal(
    val _links: Links,
    val age: String,
    val attributes: Attributes,
    val breeds: Breeds,
    val coat: String,
    val colors: Colors,
    val contact: Contact,
    val description: String,
    val distance: Any,
    val environment: Environment,
    val gender: String,
    val id: Int,
    val name: String,
    val organization_id: String,
    val photos: List<Photo>,
    val published_at: String,
    val size: String,
    val species: String,
    val status: String,
    val tags: List<String>,
    val type: String,
    val url: String,
    val videos: List<Video>
)