package com.example.acpractica1.usecases

import com.example.acpractica1.data.CountriesRepository
import com.example.acpractica1.domain.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindCountryByNameUseCase @Inject constructor(private val countriesRepository: CountriesRepository) {
    operator fun invoke(name: String): Flow<Country?> {
        return countriesRepository.findCountryByName(name)
    }
}