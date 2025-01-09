package com.example.acpractica1.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT * FROM DbCountry")
    fun fetchAllCountries(): Flow<List<DbCountry>>

    @Query("SELECT * FROM DbCountry WHERE cname = :cname")
    fun findCountryByName(cname: String): Flow<DbCountry?>

    @Query("SELECT * FROM DbCountry WHERE ccontinent = :ccontinent")
    fun fetchCountriesByCont(ccontinent: String): Flow<List<DbCountry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountries(countries: List<DbCountry>)

    @Query("UPDATE DbCountry SET gaymable = :gaymable WHERE cname = :cname")
    suspend fun updateGaymable(cname: String, gaymable: Boolean)

    @Query("SELECT COUNT(*) FROM DbCountry")
    suspend fun countCountries(): Int
}