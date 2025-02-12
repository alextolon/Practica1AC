package com.example.acpractica1.framework

import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import com.example.acpractica1.domain.Country
import com.example.acpractica1.framework.remote.CountriesService
import com.example.acpractica1.framework.remote.CountryResponse
import javax.inject.Inject

class CountriesServerDataSource @Inject constructor(
    private val countriesService: CountriesService
) : CountriesRemoteDataSource {
    // Función que recupera el set de países al completo
    override suspend fun fetchAllCountries(): List<Country> =
        countriesService // ahora como abstracción en forma de interface
            // instancia un objeto CountriesClient para así...
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
    override suspend fun fetchCountriesByCont(continent: String): List<Country> =
        countriesService // ahora como abstracción en forma de interface
            // instancia un objeto CountriesClient para así...
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
    override suspend fun findCountryByName(name: String): Country =
        countriesService // ahora como abstracción en forma de interface
            // instancia un objeto CountriesClient para así...
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

private fun CountryResponse.toDomainModel(): Country =
    Country(
        cname = name ?: "",
        ccode = code ?: "",
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
        gaymable = false
    )
