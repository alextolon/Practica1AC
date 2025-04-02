package com.example.acpractica1

import com.example.acpractica1.usecases.FetchCountriesByContUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchCountries_By_ContUseCaseTest {
    @Test
    fun `Invoke calls repository`() {
        val countryFlow = flowOf(sampleCountries("Argentina", "Uruguay"))
        val usecase = FetchCountriesByContUseCase(mock {
            on { fetchCountriesByCont( "South America")}  doReturn countryFlow })

        val result = usecase("South America")

        assertEquals(countryFlow, result)
    }
}