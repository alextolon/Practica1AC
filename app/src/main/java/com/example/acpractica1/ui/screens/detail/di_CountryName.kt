package com.example.acpractica1.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.example.acpractica1.ui.screens.NavArgs

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @CountryName
    fun provideMovieId(savedStateHandle: SavedStateHandle): String {
        // Revisar este ""
        return savedStateHandle[NavArgs.CountryName.key] ?: ""
    }

}