package com.example.acpractica1.data

import androidx.compose.runtime.MutableState

// Esta clase fundamenta el repositorio
class CountriesRepository {
    // Función que recupera el set de países al completo
    suspend fun fetchAllCountries(): List<Country> =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API que recoge el objeto
            // CountriesResponse devuelto por la API contiene una lista JSON
            // de países que presenta su elemento raiz "data"
            .fetchAllCountries()
            // por eso hay que referirse ahora a "data" de manera que se
            // pueda entrar a los datos de todos los países que contiene
            .data
            // llamando a la función que realiza el mapeo
            // al tipo Country de cada pais contenido
            .map { it.toDomainModel() }
    // Función que busca un set de países por continente
    suspend fun fetchCountriesByCont(continent: MutableState<String>): List<Country> =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API que recoge el objeto
            // CountriesResponse devuelto por la API contiene una lista JSON
            // de países que presenta su elemento raiz "data"
            .fetchCountriesByCont(continent)
            // por eso hay que referirse ahora a "data" de manera que se pueda
            // entrar a los datos de los países de ese continente que contiene
            .data
            // llamando a la función que realiza el mapeo
            // al tipo Country de cada pais contenido
            .map { it.toDomainModel() }
    // Función que busca un pais concreto
    suspend fun findCountryByName(name: String): Country =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API que recoge el objeto
            // CountryDataResponse devuelto por la API contiene una lista JSON
            // de países que presenta su elemento raiz "data"
            .findCountryByName(name)
            // por eso hay que referirse ahora a "data" de manera que se
            // pueda entrar al detalle de los datos que contiene viniendo
            // recogidos en sendos CountryResponse
            .data
            // llama a la función que realiza el mapeo al tipo Country
            .toDomainModel()
}

// Con esta función de extensión se realiza el mapeo de datos
// de la API a la dataclass de tipo Country
private fun CountryResponse.toDomainModel(): Country =
    Country(
        ccode = code ?: "",
        cname = name ?: "",
        ccapital = capital ?: "",
        ccontinent = continent ?: "",
        cflag = href?.flag ?: "",
        cpopul = population ?: "",
        cpres = current_president?.name ?: "",
        cfname = fname ?: "",
        ccurrency = currency ?: "",
    )
