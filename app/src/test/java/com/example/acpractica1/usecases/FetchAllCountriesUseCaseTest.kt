package com.example.acpractica1.usecases

import com.example.acpractica1.sampleCountries
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

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