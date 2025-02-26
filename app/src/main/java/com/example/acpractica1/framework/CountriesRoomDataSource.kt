package com.example.acpractica1.framework

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.domain.Country
import com.example.acpractica1.framework.database.CountriesDao
import com.example.acpractica1.framework.database.DbCountry
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountriesRoomDataSource @Inject constructor(private val countriesDao: CountriesDao) :
    CountriesLocalDataSource {

    override val countries = countriesDao.fetchAllCountries()
        .map{ lista -> lista.map{ it.toDomainCountry() } }

    override fun fetchCountriesByCont(continent: String) = countriesDao.fetchCountriesByCont(continent)
        .map{ lista -> lista.map{ it.toDomainCountry() } }

    override fun findCountryByName(name: String) = countriesDao.findCountryByName(name)
        .map{ it?.toDomainCountry() }

    override suspend fun saveCountries(countries: List<Country>) = countriesDao.saveCountries(countries
        .map{ it.toDomainDbCountry() }
    )

    override suspend fun updateGaymable(country: Country) = countriesDao.updateGaymable(country.toDomainDbCountry().cname, !country.toDomainDbCountry().gaymable)

    override suspend fun isEmpty() = countriesDao.countCountries() == 0
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