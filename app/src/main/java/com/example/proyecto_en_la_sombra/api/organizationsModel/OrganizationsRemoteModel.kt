package com.example.proyecto_en_la_sombra.api.organizationsModel

data class OrganizationsRemoteModel(
    val organizations: List<Organization>,
    val pagination: Pagination
)