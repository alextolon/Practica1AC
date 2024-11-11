package com.example.acpractica1.data

import java.time.LocalDate
import java.time.LocalDateTime

// Esta data class define los datos que se van utilizar
// de entre los que se recogen en un CountryResponse
data class Country(
    val ccode: String,
    val cname: String,
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