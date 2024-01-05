package com.example.proyecto_en_la_sombra.Repository

import com.example.proyecto_en_la_sombra.Model.Protectora
import com.example.proyecto_en_la_sombra.Model.Valoracion

class valoracionRepository(instance: AplicacionDB) {
    var store = instance

    suspend fun setValoracion(idCliente: Long, idProtectora: String, valoracion: String) =
        store.valoracionDAO().setValoracion(idCliente, idProtectora, valoracion)

    suspend fun getValoracionByIdProtectora(idProtectora: String): List<Valoracion> =
        store.valoracionDAO().getValoracionByIdProtectora(idProtectora)

}