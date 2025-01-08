package com.example.acpractica1.usecases

import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.flow.Flow

class FindCountryByNameUseCase(private val countriesRepository: CountriesRepository) {
    operator fun invoke(name: String): Flow<Country?> {
        return countriesRepository.findCountryByName(name)
    }
}