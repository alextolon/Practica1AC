package com.example.acpractica1.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
// Esta interface implementa el servicio de peticiones a la API que
// nos interese apuntando a los endpoints de la misma
interface CountriesService {
    // No es lo mismo solicitar el set de países al completo...
    @GET("countries")
    suspend fun fetchAllCountries(): CountriesResponse

    // ...que solicitar un set de países por continente...
    @GET("countries?continent={continent}")
    suspend fun fetchCountriesByCont(@Query("continent") continent: String): CountriesResponse

    // ...que pedir solamente uno concreto
    @GET("countries/{name}")
    suspend fun findCountryByName(@Path("name") name: String): CountryDataResponse
    // De ahí que el carrito que recoge resultados sea diferente
    // Es más, @Path permite incorporar la variable a la propia ruta de petición del GET
    // En cambio, @Query le pasa parámetros para realizar una consulta y recuperar los datos
}