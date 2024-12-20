package com.example.acpractica1.data
// Importante: generar, a partir del resultado JSON que devuelve CADA consulta
// a la API (con Gemini o plugin), las data classes siguientes respetando
// TODOS los campos aunque luego no se usen todos.
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Esta data class está preparada para recibir varios sets de países
// Por eso será utilizada para recopilar tanto el total de países
// como los de cada continente
@Serializable
data class CountriesResponse(
    val data: List<CountryResponse>,
    val links: Links?,
    val meta: Meta?
)

// Esta otra en cambio está preparada para recibir los datos que vienen
// de un solo pais, pero dada la peculiaridad de la API que devuelve
// los datos dentro de un elemento "data" hay que hay que generar esta
// data class que hace de nexo...
@Serializable
data class CountryDataResponse(
    val data: CountryResponse
)

// ...con esta otra que contiene ya el detalle de los datos que vienen
// de un solo pais.
@Serializable
data class CountryResponse(
    val name: String?,
    @SerialName("full_name") val fname: String?,
    val capital: String?,
    val iso2: String?,
    val iso3: String?,
    val covid19: Covid19?, // Tipo compuesto, se desglosa más abajo
    val current_president: CurrentPresident?, // Tipo compuesto, se desglosa más abajo
    val currency: String?,
    @SerialName("phone_code") val code: String?,
    val continent: String?,
    val description: String?,
    val size: String?,
    val independence_date: String?,
    val population: String?,
    val href: Href?  // Tipo compuesto, se desglosa más abajo
)

// Desglose del tipo compuesto Covid19
@Serializable
data class Covid19(
    //val total_case: String?,
    @SerialName("total_case") val cases: String?,
    //val total_deaths: String?,
    @SerialName("total_deaths") val deaths: String?,
    //val last_updated: String?
    @SerialName("last_updated") val lastdate: String?,
)

// Desglose del tipo compuesto CurrentPresident
@Serializable
data class CurrentPresident(
    val name: String?,
    val gender: String?,
    val appointment_start_date: String?,
    val appointment_end_date: String?,
    val href: HrefPresident? // Tipo compuesto, se desglosa más abajo
)

// Desglose del tipo compuesto HrefPresident
@Serializable
data class HrefPresident(
    val self: String?,
    val country: String?,
    val picture: String?
)

// Desglose del tipo compuesto Href
@Serializable
data class Href(
    val self: String?,
    val states: String? = null,
    val presidents: String? = null,
    val flag: String?
)

// Desglose del tipo compuesto Links
@Serializable
data class Links(
    val first: String?,
    val last: String?,
    val prev: String?,
    val next: String?
)

// Desglose del tipo compuesto Meta
@Serializable
data class Meta(
    val current_page: Int?,
    val from: Int?,
    val last_page: Int?,
    val path: String?,
    val per_page: Int?,
    val to: Int?,
    val total: Int?
)