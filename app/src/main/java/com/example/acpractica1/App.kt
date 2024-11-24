package com.example.acpractica1

import android.app.Application
import androidx.room.Room
import com.example.acpractica1.data.datasource.database.CountriesDatabase

class App: Application() {
    lateinit var db: CountriesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, CountriesDatabase::class.java, "countries-db")
            .build()
    }
}