package com.example.acpractica1.data

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import com.example.acpractica1.data.datasource.CountriesRepository
import com.example.acpractica1.domain.Country
import com.example.acpractica1.sampleCountries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

fun buildCountriesRepositoryWith(
    localData: List<Country> = emptyList(),
    remoteData: List<Country> = emptyList()
): CountriesRepository {
    val localDataSource = FakeLocalDataSource().apply { inMemoryCountries.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { countries = remoteData }
    return CountriesRepository(localDataSource, remoteDataSource)
}

class FakeLocalDataSource : CountriesLocalDataSource {

    val inMemoryCountries = MutableStateFlow<List<Country>>(emptyList())

    override val countries = inMemoryCountries

    override fun fetchCountriesByCont(continent: String): Flow<List<Country>> =
        inMemoryCountries.transform {  }

    override fun findCountryByName(name: String): Flow<Country?> =
        inMemoryCountries.map { it.firstOrNull { country -> country.cname == name } }

    override suspend fun saveCountries(countries: List<Country>) {
        inMemoryCountries.value = countries
    }

    override suspend fun updateGaymable(country: Country) {
        TODO("Not yet implemented")
    }

    override suspend fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}

class FakeRemoteDataSource : CountriesRemoteDataSource {

    var countries = sampleCountries("Argentina", "Spain", "France", "Belgium")

    var countriesCont = sampleCountries("Argentina", "Uruguay", "Colombia", "Paraguay")

    override suspend fun fetchAllCountries() = countries

    override suspend fun fetchCountriesByCont(continent: String): List<Country> = countriesCont

    override suspend fun findCountryByName(name: String): Country = countries.first { it.cname == name }
}