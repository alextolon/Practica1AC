package com.example.acpractica1.data

import com.example.acpractica1.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object CountriesClient {
    // Token de autenticación para acceder a la API cogido de local.properties
    private const val BTOK = BuildConfig.RCDB_API_KEY

    // Configura el cliente HTTP con un interceptor para agregar el token en cada petición
    private val okHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(bearerTokenInterceptor(BTOK)) // Interceptor para agregar el token Bearer
        .build() // Crea el cliente HTTP configurado

    // Configura la instancia de Json para serializar y deserializar datos
    private val json = Json {
        ignoreUnknownKeys = true // Ignorar campos desconocidos en las respuestas JSON
    }

    // Configura e instancia Retrofit para realizar peticiones HTTP a la API de países
    val instance = Retrofit.Builder()
        .baseUrl("https://restfulcountries.com/api/v1/") // Dirección base de la API
        .client(okHttpClient) // Asocia el cliente HTTP configurado con Retrofit
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Convierte JSON a objetos Kotlin
        .build() // Construye la instancia de Retrofit
        .create<CountriesService>() // Crea la implementación de la interfaz de servicio
}

// Interceptor que añade el token de autenticación a cada solicitud
private fun bearerTokenInterceptor(token: String): Interceptor {
    return Interceptor { chain ->
        // Crea una nueva solicitud con el encabezado de autenticación Bearer
        val request = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer $token") // Añade el token de autenticación al header
            .build()
        // Procede con la solicitud
        chain.proceed(request)
    }
}