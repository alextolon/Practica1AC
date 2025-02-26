package com.example.acpractica1.usecases

import com.example.acpractica1.data.datasource.CountriesRepository
import com.example.acpractica1.domain.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllCountriesUseCase @Inject constructor (private val countriesRepository: CountriesRepository) {
    operator fun invoke(): Flow<List<Country>> {
        return countriesRepository.countries
    }
}