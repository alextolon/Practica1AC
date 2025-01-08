package com.example.acpractica1.usecases

import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.data.Country
import kotlinx.coroutines.flow.Flow

class FetchCountriesByContUseCase(private val countriesRepository: CountriesRepository) {
    operator fun invoke(optSelected: String): Flow<List<Country>> {
        return countriesRepository.fetchCountriesByCont(optSelected)
    }
}