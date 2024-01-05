package com.example.proyecto_en_la_sombra.Repository

import com.example.proyecto_en_la_sombra.Model.Cliente


class clientRepository(instance: AplicacionDB) {
    var store: AplicacionDB = instance

    suspend fun getClientById(id: Long): Cliente = store.clienteDAO().getClientById(id)

    suspend fun getClienteByEmail(email: String): Cliente =
        store.clienteDAO().getClienteByEmail(email)

    suspend fun insertCliente(cliente: Cliente) = store.clienteDAO().insertCliente(cliente)
}