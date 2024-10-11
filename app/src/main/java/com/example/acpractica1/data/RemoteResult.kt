package com.example.acpractica1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Esta dataclass define los datos de la API que nos interesan
// para esta consulta donde se pueden recuperar varios países
@Serializable
data class RemoteResult(
    val results: List<RemoteCountry>
)

// Esta dataclass define los datos de la API que nos interesan para
// esta consulta donde se busca recuperar info de un país concreto
@Serializable
data class RemoteCountry(
    // Aquellos identificadores de la API cuyos nombres no coindcidan
    // con los nombres de las propiedades de esta dataclass se
    // "anotan" con @SerialName
    @SerialName("phone_code") val code: String,
    val name: String,
    val capital: String,
    val continent: String,
    val population: String,
    val flag: String,
)