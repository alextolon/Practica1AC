package com.example.acpractica1.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acpractica1.data.Country

@Dao
interface CountriesDao {

    @Query("SELECT * FROM Country")
    suspend fun fetchAllCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE cname = :cname")
    suspend fun findCountryByName(cname: String): Country

    @Query("SELECT * FROM Country WHERE ccontinent = :ccontinent")
    suspend fun fetchCountriesByCont(ccontinent: String): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountries(countries: List<Country>)
}