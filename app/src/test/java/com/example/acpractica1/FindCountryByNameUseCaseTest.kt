package com.example.acpractica1

import com.example.acpractica1.usecases.FindCountryByNameUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindCountryByNameUseCaseTest {
    @Test
    fun `Invoke calls repository`() {
        val countryFlow = flowOf(sampleCountry("Argentina"))
        val usecase = FindCountryByNameUseCase(mock {
            on { findCountryByName( "Argentina")}  doReturn countryFlow })

        val result = usecase("Argentina")

        assertEquals(countryFlow, result)
    }
}