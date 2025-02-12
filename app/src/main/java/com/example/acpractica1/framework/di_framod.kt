package com.example.acpractica1.framework

import android.app.Application
import androidx.room.Room
import com.example.acpractica1.framework.database.CountriesDatabase
import com.example.acpractica1.framework.remote.CountriesService
import com.example.acpractica1.framework.remote.CountriesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object di_framod {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        CountriesDatabase::class.java,
        "countries-db"
    ).build()

    @Provides
    fun provideCountriesDao(db: CountriesDatabase) = db.countriesDao()

    @Provides
    @Singleton
    // Da el problema hasta que CountriesClient no se convierta en clase (ahora es Object)
    fun provideCountriesService(@Named("bearerToken") bearerToken: String): CountriesService = CountriesClient(
        bearerToken
    ).instance
}