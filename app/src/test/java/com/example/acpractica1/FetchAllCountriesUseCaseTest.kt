package com.example.acpractica1

import com.example.acpractica1.usecases.FetchAllCountriesUseCase
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FetchAllCountriesUseCaseTest {
    @Test
    fun `Invoke calls repository`() {
        // Given / Arrange
        // sampleCountries se coge de un helper que simula la recepción de un país
        val countryFlow = flowOf(sampleCountries("Argentina", "Spain"))
        val useCase = FetchAllCountriesUseCase( mock {
            on { countries } doReturn countryFlow
        })

        // When / Act
        val result = useCase()

        // Then / Assert
        assertEquals(countryFlow, result)
    }
}