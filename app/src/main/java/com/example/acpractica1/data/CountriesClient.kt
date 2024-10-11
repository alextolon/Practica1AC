package com.example.acpractica1.data

import com.example.acpractica1.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object CountriesClient {
    private const val BTOK = "1492|C8cW8E2bPcpgQs1LssTzREt3LhBes64vh6Lf6xkg"
    // Construye un interceptor que permita adaptar cada petición a la API
    // para ser configurado a continuación ...
    private val okHttpClient = okhttp3.OkHttpClient.Builder()
        // El interceptor está fundamentado en una función
        .addInterceptor(bearerTokenInterc(BTOK))
        // para acabar instanciando un objeto okHttpClient con
        // la configuración especificada en el paso anterior
        .build()

    // Al recuperar datos de una API en forma de cadena JSON hay
    // que crear tantas properties como campos tenga la estructura.
    private val json = Json {
        // Con esta especie de "adapter" activado se le indica que ignore
        // los datos que no vamos a utilizar de dicha cadena JSON.
        ignoreUnknownKeys = true
    }

    // Construye el objeto Retrofit para ser configurado a continuación ...
    val instance = Retrofit.Builder()
        // 1) indicando su dirección base
        .baseUrl("https://restfulcountries.com/api/v1/")
        // 2) especificando el cliente e incorporando el interceptor creado antes
        .client(okHttpClient)
        // 3) serializando a objeto Kotlin dado que los datos
        // de la API se muestran en formato JSON
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        // 5) instanciando un objeto Retrofit con la configuración
        // especificada en los pasos anteriores
        .build()
        // 6) creando una implementación de la interfaz CountriesService a partir de la instancia
        // de Retrofit. Recuerda que en esta interfaz están definidos los endpoints de la API
        // que se van a consumir.
        .create<CountriesService>()

}

// Función que gestiona la labor de modificación del request y
// obtención del response a cargo del interceptor
private fun bearerTokenInterc(chain: Interceptor.Chain) = chain.proceed(
    chain.request()
        .newBuilder()
        .header("Authorization", "Bearer $token")
        .build()
)

/*
// Función que gestiona la labor de modificación del request y
// obtención del response a cargo del interceptor
private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
    chain.request()
        .newBuilder()
        .url(chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.RCDB_API_KEY)
            .build())
        .build()
)
*/