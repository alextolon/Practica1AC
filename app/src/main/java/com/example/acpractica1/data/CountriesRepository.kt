package com.example.acpractica1.data

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import com.example.acpractica1.domain.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

// Esta clase fundamenta el repositorio

class CountriesRepository @Inject constructor(
    private val localDataSource: CountriesLocalDataSource,
    private val remoteDataSource: CountriesRemoteDataSource
) {
    // Función que recupera el set de países al completo de la BD que, si estuviera
    // vacía tiraría de la API y rellenaría la BD
    val countries : Flow<List<Country>> = localDataSource.countries.transform { localCountries ->
        val countries = localCountries.takeIf { it.isNotEmpty() }
            ?: remoteDataSource.fetchAllCountries().also {
                localDataSource.saveCountries(it)
            }
        emit(countries)
    }

    // Función que busca un set de países por continente
    fun fetchCountriesByCont(continent: String): Flow<List<Country>> = localDataSource.fetchCountriesByCont(continent).transform { localCountries ->
        val countriesCont = localCountries.takeIf { it.isNotEmpty() }
            ?: remoteDataSource.fetchCountriesByCont(continent).also {
                localDataSource.saveCountries(it)
            }
        emit(countriesCont)
    }

    // Función que busca un pais concreto
    fun findCountryByName(name: String): Flow<Country?> = localDataSource.findCountryByName(name)
        .transform { localCountry ->
            val country = localCountry
                ?: remoteDataSource.findCountryByName(name).also {
                    localDataSource.saveCountries(listOf(it))
                }
            emit(country)
        }

    suspend fun cambiaFriendly(country: Country) {
        localDataSource.updateGaymable(country)
    }
}