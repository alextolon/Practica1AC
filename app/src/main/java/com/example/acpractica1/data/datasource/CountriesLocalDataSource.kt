package com.example.acpractica1.data.datasource

import com.example.acpractica1.data.Country
import com.example.acpractica1.data.datasource.database.CountriesDao

class CountriesLocalDataSource(private val countriesDao: CountriesDao) {

    suspend fun fetchAllCountries() = countriesDao.fetchAllCountries()

    suspend fun fetchCountriesByCont(continent: String) = countriesDao.fetchCountriesByCont(continent)

    suspend fun findCountryByName(name: String) = countriesDao.findCountryByName(name)

    suspend fun saveCountries(countries: List<Country>) = countriesDao.saveCountries(countries)

    suspend fun isEmpty() = countriesDao.countCountries() == 0
}