package com.example.acpractica1

import com.example.acpractica1.domain.Country

fun sampleCountry(cname: String) =  Country(
    cname = cname,
    ccode = "314",
    ccapital = "Buenos Aires",
    ccontinent = "South America",
    cflag = "https://restfulcountries.com/assets/images/flags/Argentina.png",
    cpopul = "52,000,123",
    cpres = "Milei",
    cfname = "The Argentine Republic",
    ccurrency = "ARS",
    ccases = "1,418,807",
    cdeaths = "38,473",
    ccovupdated = "2020-12-01T08:35:25.000000Z",
    gaymable = false
)

fun sampleCountries(vararg cnames: String) = cnames.map { sampleCountry(it) }