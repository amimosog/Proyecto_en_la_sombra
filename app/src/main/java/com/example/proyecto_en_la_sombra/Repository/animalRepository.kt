package com.example.proyecto_en_la_sombra.Repository

import com.example.proyecto_en_la_sombra.Model.Animal
import com.example.proyecto_en_la_sombra.api.RetrofitService
import com.example.proyecto_en_la_sombra.api.TypeModel.TypeRemoteModel
import com.example.proyecto_en_la_sombra.api.model.RemoteModelPage
import com.example.proyecto_en_la_sombra.api.model.RemoteResult
import com.example.proyecto_en_la_sombra.auth

class animalRepository(instance: AplicacionDB, service: RetrofitService) {
    var store: AplicacionDB = instance
    var source = service

    suspend fun getAnimalById(idAnimal: Long): com.example.proyecto_en_la_sombra.Model.Animal =
        store.animalDAO().getAnimalById(idAnimal)

    suspend fun insertAnimal(animal: com.example.proyecto_en_la_sombra.Model.Animal) =
        store.animalDAO().insertAnimal(animal)

    suspend fun getAnimalByOrgId(idOrg: String): List<Animal> = store.animalDAO().getAnimalsByOrgId(idOrg)

    suspend fun getAnimalById(idAnimal: String): RemoteResult =
        source.getAnimals(auth, idAnimal)

    suspend fun getAnimalsRandom(sort: String): RemoteModelPage =
        source.getAnimalsRandom(auth, sort)

    suspend fun getAnimalsName(sort: String, name: String): RemoteModelPage =
        source.getAnimalsName(auth, sort, name)

    suspend fun getAnimalsByOrganization(organization: String): RemoteModelPage =
        source.getAnimalsByOrganization(auth, organization)

    suspend fun getAnimalsFilters(
        sort: String,
        type: String,
        size: String,
        gender: String,
        age: String
    ): RemoteModelPage = source.getAnimalsFilters(auth, sort, type, size, gender, age)

    suspend fun getSearchTypes(): TypeRemoteModel = source.getSearchTypes(auth)

}