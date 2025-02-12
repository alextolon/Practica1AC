package com.example.acpractica1

import android.app.Application
import androidx.room.Room
import com.example.acpractica1.framework.database.CountriesDatabase
import dagger.hilt.android.HiltAndroidApp

// Hay que desplazar la inicialización de la BD como @Provides en el DI en framework,
// dejando vacía la class App
// Hay que usar la anotación que activa la generación de código Hilt para la app

@HiltAndroidApp
class App: Application()


/*{ // Una forma de tener aparte el inicio de la BD
    lateinit var db: CountriesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        // Se crea la conexión con la BD a través de Room
        db = Room.databaseBuilder(this, CountriesDatabase::class.java, "countries-db")
            .build()
    }
}*/