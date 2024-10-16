package com.example.acpractica1.data
// Esta clase fundamenta el repositorio
class CountriesRepository {
    // Función que recupera el set de países al completo
    suspend fun fetchAllCountries(): List<Country> =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API
            .fetchAllCountries()
            // El RemoteResult devuelto por la API es un objeto que contiene
            // una lista JSON de países cuya etiqueta raiz en la API es data
            .data
            // recorre results llamando a la función que realiza el mapeo
            // al tipo Country de cada pais
            .map { it.toDomainModel() }
    // Función que busca un set de países por continente
    suspend fun fetchCountriesByCont(continent: String): List<Country> =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API
            .fetchCountriesByCont(continent)
            // El RemoteResult devuelto por la API es un objeto que contiene
            // una lista JSON de países cuya etiqueta raiz en la API es data
            .data
            // recorre results llamando a la función que realiza el mapeo
            // al tipo Country de cada pais
            .map { it.toDomainModel() }
    // Función que busca un pais concreto
    suspend fun findCountryByName(name: String): Country =
        CountriesClient
            // instancia un objeto CountriesClient para así...
            .instance
            // realizar esta petición concreta a la API
            .findCountryByName(name)
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
