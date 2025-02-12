package com.example.acpractica1.framework

import com.example.acpractica1.data.datasource.CountriesLocalDataSource
import com.example.acpractica1.data.datasource.CountriesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworkCountryModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: CountriesRoomDataSource): CountriesLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: CountriesServerDataSource): CountriesRemoteDataSource

}