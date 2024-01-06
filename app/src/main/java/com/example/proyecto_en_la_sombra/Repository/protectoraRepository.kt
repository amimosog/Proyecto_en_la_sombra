package com.example.proyecto_en_la_sombra.Repository

import com.example.proyecto_en_la_sombra.Model.Protectora
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrgRemoteModel
import com.example.proyecto_en_la_sombra.api.organizationsModel.OrganizationsRemoteModel
import com.example.proyecto_en_la_sombra.auth

class protectoraRepository(instance: AplicacionDB, service: RetrofitService) {
    var store = instance
    var source = service

    suspend fun getOrganizaciones(): List<Protectora> = store.protectoraDAO().getOrganizaciones()

    suspend fun getOrganizacionId(idProtectora: Long): Protectora =
        store.protectoraDAO().getOrganizacionId(idProtectora)

    suspend fun insertOrganizacion(organizacion: Protectora) =
        store.protectoraDAO().insertOrganizacion(organizacion)

    suspend fun getOrganizations(): OrganizationsRemoteModel = source.getOrganizations(auth)

    suspend fun getUniqueOrganization(idProtectora: String): OrgRemoteModel =
        source.getUniqueOrganization(auth, idProtectora)

}