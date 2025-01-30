package com.example.acpractica1.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCountry::class], version = 1, exportSchema = false)
abstract class CountriesDatabase: RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}