package com.example.acpractica1.domain

// Esta data class define los datos base que se van utilizar
// en la aplicación

data class Country(
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
    val ccovupdated: String,
    val gaymable: Boolean
)