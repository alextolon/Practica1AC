package com.example.acpractica1.data.datasource

import com.example.acpractica1.domain.Country
import kotlinx.coroutines.flow.Flow

interface CountriesLocalDataSource {
    val countries: Flow<List<Country>>
    fun fetchCountriesByCont(continent: String): Flow<List<Country>>
    fun findCountryByName(name: String): Flow<Country?>

    suspend fun saveCountries(countries: List<Country>)

    suspend fun updateGaymable(country: Country)

    suspend fun isEmpty(): Boolean
}

