package com.example.proyecto_en_la_sombra.apiimport retrofit2.Retrofitimport retrofit2.converter.gson.GsonConverterFactoryimport retrofit2.http.GETimport retrofit2.http.Queryinterface RetrofitService {    @GET("")    suspend fun getAnimals(        @Query("api_key") apiKey: String,    )    object RetrofitServiceFactory {        fun makeRetrofitService(): RetrofitService {            return Retrofit.Builder()                .baseUrl("https://api.petfinder.com/v2/animals")                .addConverterFactory(GsonConverterFactory.create())                .build().create(RetrofitService::class.java)        }    }}