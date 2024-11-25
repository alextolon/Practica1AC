package com.example.acpractica1.data

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource

// Esta clase fundamenta el repositorio
class CountriesRepository(
    private val localDataSource: CountriesLocalDataSource,
    private val remoteDataSource: CountriesRemoteDataSource
) {
    // Función que recupera el set de países al completo
    suspend fun fetchAllCountries(): List<Country> {
        if(localDataSource.isEmpty()) {
            val countries = remoteDataSource.fetchAllCountries()
            localDataSource.saveCountries(countries)
        }
        return localDataSource.fetchAllCountries()
    }
        /*CountriesClient
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
            .map { it.toDomainModel() }*/
    // Función que busca un set de países por continente
    suspend fun fetchCountriesByCont(continent: String): List<Country> {
        if(localDataSource.isEmpty()) {
            val countries = remoteDataSource.fetchCountriesByCont(continent)
            localDataSource.saveCountries(countries)
        }
        return localDataSource.fetchCountriesByCont(continent)
    }
        /*CountriesClient
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
            .map { it.toDomainModel() }*/
    // Función que busca un pais concreto
    suspend fun findCountryByName(name: String): Country {
        if(localDataSource.findCountryByName(name) == null) {
                val countries = remoteDataSource.findCountryByName(name)
                localDataSource.saveCountries(listOf(countries))
            }
            return checkNotNull(localDataSource.findCountryByName(name))
    }
        /*CountriesClient
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
            .toDomainModel()*/
}

// Con esta función de extensión se realiza el mapeo de datos
// de la API a la dataclass de tipo Country
/*private fun CountryResponse.toDomainModel(): Country =
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
        ccases = covid19?.cases ?: "",
        cdeaths = covid19?.deaths ?: "",
        ccovupdated = covid19?.lastdate ?: "",
    )
*/