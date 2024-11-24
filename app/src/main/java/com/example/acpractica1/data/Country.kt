package com.example.acpractica1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Esta data class define los datos que se van utilizar
// de entre los que se recogen en un CountryResponse
@Entity
data class Country(
    @PrimaryKey(autoGenerate = false)
    val cname: String,
    val ccode: String,
    val ccapital: String,
    val ccontinent: String,
    val cflag: String,
    val cpopul: String,
    val cpres: String,
    val cfname: String,
    val ccurrency: String,
    val ccases: String,
    val cdeaths: String,
    val ccovupdated: String
)