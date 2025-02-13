package com.example.acpractica1

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
// Suministra la inyección de dependencias del BEARER TOKEN
object di_appmod {
    // Preparamos la recogida del BearerToken
    @Provides
    @Singleton
    @Named("bearerToken")
    fun provideBearerToken() = BuildConfig.RCDB_API_KEY

}
