package com.example.acpractica1.data.datasource

import com.example.acpractica1.domain.Country

interface CountriesRemoteDataSource {
    // Función que recupera el set de países al completo
    suspend fun fetchAllCountries(): List<Country>

    // Función que busca un set de países por continente
    suspend fun fetchCountriesByCont(continent: String): List<Country>

    // Función que busca un pais concreto
    suspend fun findCountryByName(name: String): Country
}

