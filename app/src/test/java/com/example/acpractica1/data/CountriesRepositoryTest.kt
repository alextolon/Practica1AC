package com.example.acpractica1.data

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import com.example.acpractica1.data.datasource.CountriesRepository
import com.example.acpractica1.domain.Country
import com.example.acpractica1.sampleCountries
import com.example.acpractica1.sampleCountry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.kotlin.argThat

@RunWith(MockitoJUnitRunner::class)
class CountriesRepositoryTest {

    @Mock
    lateinit var localDataSource: CountriesLocalDataSource

    @Mock
    lateinit var remoteDataSource: CountriesRemoteDataSource

    private lateinit var repository: CountriesRepository

    @Before
    fun setUp() {
        repository = CountriesRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `countries are taken from local data source if available`(): Unit = runBlocking {
        val localCountries = sampleCountries("Argentina", "Spain")
        whenever(localDataSource.countries).thenReturn(flowOf(localCountries))

        val result = repository.countries

        assertEquals(localCountries, result.first())
    }

    @Test
    fun `countries are saved to local data source if empty`(): Unit = runBlocking {
        val localCountries = emptyList<Country>()
        val remoteCountries = sampleCountries("Argentina", "Spain")
        whenever(localDataSource.countries).thenReturn(flowOf(localCountries))
        whenever(remoteDataSource.fetchAllCountries()).thenReturn(remoteCountries)

        repository.countries.first()

        verify(localDataSource).saveCountries(remoteCountries)
    }

    @Test
    fun `Finding country by name is done in local data source`(): Unit = runBlocking {
        val country = sampleCountry("Argentina")
        whenever(localDataSource.findCountryByName("Argentina")).thenReturn(flowOf(country))

        val result = repository.findCountryByName("Argentina")

        assertEquals(country, result.first())
    }

    /*@Test
    fun `Toggling gaymable updates local data source`(): Unit = runBlocking {
        val country = sampleCountry("Argentina")

        repository.cambiaFriendly(country)

        verify(localDataSource).saveCountries(argThat { get(0).cname == "Argentina" })
    }*/

    @Test
    fun `Switching gaymable to nogaymable country`(): Unit = runBlocking {
        val country = sampleCountry("Argentina").copy(gaymable = false)

        repository.cambiaFriendly(country)

        verify(localDataSource).saveCountries(argThat { get(0).gaymable })
    }

    @Test
    fun `Switching nogaymable to gaymable country`(): Unit = runBlocking {
        val country = sampleCountry("Argentina").copy(gaymable = true)

        repository.cambiaFriendly(country)

        verify(localDataSource).saveCountries(argThat { !get(0).gaymable })
    }
}