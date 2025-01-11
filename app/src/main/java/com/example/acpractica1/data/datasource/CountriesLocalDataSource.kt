package com.example.acpractica1.data.datasource

import com.example.acpractica1.data.datasource.database.DbCountry
import com.example.acpractica1.data.datasource.database.CountriesDao
import com.example.acpractica1.domain.Country
import kotlinx.coroutines.flow.map

class CountriesLocalDataSource(private val countriesDao: CountriesDao) {

    val countries = countriesDao.fetchAllCountries()
        .map{ lista -> lista.map{ it.toDomainCountry() } }

    fun fetchCountriesByCont(continent: String) = countriesDao.fetchCountriesByCont(continent)
        .map{ lista -> lista.map{ it.toDomainCountry() } }

    fun findCountryByName(name: String) = countriesDao.findCountryByName(name)
        .map{ it?.toDomainCountry() }

    suspend fun saveCountries(countries: List<Country>) = countriesDao.saveCountries(countries
        .map{ it.toDomainDbCountry() }
    )

    suspend fun updateGaymable(country: Country) = countriesDao.updateGaymable(country.toDomainDbCountry().cname, !country.toDomainDbCountry().gaymable)

    suspend fun isEmpty() = countriesDao.countCountries() == 0
}

private fun DbCountry.toDomainCountry(): Country =
    Country(
        cname = cname,
        ccode = ccode,
        ccapital = ccapital,
        ccontinent = ccontinent,
        cflag = cflag,
        cpopul = cpopul,
        cpres = cpres,
        cfname = cfname,
        ccurrency = ccurrency,
        ccases = ccases,
        cdeaths = cdeaths,
        ccovupdated = ccovupdated,
        gaymable = gaymable
    )

    private fun Country.toDomainDbCountry(): DbCountry =
        DbCountry(
            cname = cname,
            ccode = ccode,
            ccapital = ccapital,
            ccontinent = ccontinent,
            cflag = cflag,
            cpopul = cpopul,
            cpres = cpres,
            cfname = cfname,
            ccurrency = ccurrency,
            ccases = ccases,
            cdeaths = cdeaths,
            ccovupdated = ccovupdated,
            gaymable = gaymable
        )