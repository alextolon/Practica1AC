plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    // Para Hilt
    // No hace falta implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.core)
    //implementation(libs.hilt.android)
    //implementation(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}