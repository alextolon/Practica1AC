package com.example.acpractica1.usecases

import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.flow.Flow

class FetchAllCountriesUseCase(private val countriesRepository: CountriesRepository) {
    operator fun invoke(): Flow<List<Country>> {
        return countriesRepository.countries
    }
}