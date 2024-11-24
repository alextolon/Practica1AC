package com.example.acpractica1.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.acpractica1.data.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountriesDatabase: RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}