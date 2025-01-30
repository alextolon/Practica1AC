package com.example.acpractica1.usecases

import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.domain.Country

class CambiaFriendlyUseCase(private val countriesRepository: CountriesRepository) {
    suspend operator fun invoke(country: Country) {
        return countriesRepository.cambiaFriendly(country)
    }
}