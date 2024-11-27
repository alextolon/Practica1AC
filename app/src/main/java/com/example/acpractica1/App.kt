package com.example.acpractica1

import android.app.Application
import androidx.room.Room
import com.example.acpractica1.data.datasource.database.CountriesDatabase

// Una forma de tener aparte el inicio de la BD
class App: Application() {
    lateinit var db: CountriesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        // Se crea la conexión con la BD a través de Room
        db = Room.databaseBuilder(this, CountriesDatabase::class.java, "countries-db")
            .build()
    }
}