package com.example.acpractica1.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acpractica1.data.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT * FROM Country")
    fun fetchAllCountries(): Flow<List<Country>>

    @Query("SELECT * FROM Country WHERE cname = :cname")
    fun findCountryByName(cname: String): Flow<Country?>

    @Query("SELECT * FROM Country WHERE ccontinent = :ccontinent")
    fun fetchCountriesByCont(ccontinent: String): Flow<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountries(countries: List<Country>)

    @Query("UPDATE Country SET gaymable = :gaymable WHERE cname = :cname")
    suspend fun updateGaymable(cname: String, gaymable: Boolean)

    @Query("SELECT COUNT(*) FROM Country")
    suspend fun countCountries(): Int
}