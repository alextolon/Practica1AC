package com.example.acpractica1.data.datasource

import com.example.acpractica1.data.Country
import com.example.acpractica1.data.datasource.database.CountriesDao

class CountriesLocalDataSource(private val countriesDao: CountriesDao) {

    val countries = countriesDao.fetchAllCountries()

    fun fetchCountriesByCont(continent: String) = countriesDao.fetchCountriesByCont(continent)

    fun findCountryByName(name: String) = countriesDao.findCountryByName(name)

    suspend fun saveCountries(countries: List<Country>) = countriesDao.saveCountries(countries)

    suspend fun isEmpty() = countriesDao.countCountries() == 0
}