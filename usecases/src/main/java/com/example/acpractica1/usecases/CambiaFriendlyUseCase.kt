package com.example.acpractica1.usecases

import com.example.acpractica1.data.datasource.CountriesRepository
import com.example.acpractica1.domain.Country
import javax.inject.Inject

class CambiaFriendlyUseCase @Inject constructor(private val countriesRepository: CountriesRepository) {
    suspend operator fun invoke(country: Country) {
        return countriesRepository.cambiaFriendly(country)
    }
}