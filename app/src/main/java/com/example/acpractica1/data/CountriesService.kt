package com.example.acpractica1.data

import androidx.compose.runtime.MutableState
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
// Esta interface implementa el servicio de peticiones a la API que
// nos interese apuntando a los endpoints de la misma que son útiles
interface CountriesService {
    // No es lo mismo solicitar el set de países al completo...
    @GET("countries")
    // Función que combinada con la anotación prepara la petición a la API con GET
    suspend fun fetchAllCountries(): CountriesResponse

    // ...que solicitar un set de países por continente...
    @GET("countries")
    suspend fun fetchCountriesByCont(@Query("continent") continent: String): CountriesResponse
    // @Query añade a la URL de la petición a la API con GET el nombre
    // del parámetro {continent} y su valor correspondiente continent de tipo cadena

    // ...que pedir solamente uno concreto
    @GET("countries/{name}")
    suspend fun findCountryByName(@Path("name") name: String): CountryDataResponse
    // De ahí que el carrito que recoge resultados sea diferente (sólo el de un país)
    // @Path permite incorporar el nombre de la variable a la propia ruta
    // de la petición del GET
}