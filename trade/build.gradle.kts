plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
    id("io.ktor.plugin") version "3.3.0"
}

group = "dmitriy.losev.cs"
version = "0.0.1"

dependencies {

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.content.negotiation)

    api(projects.di)

    api(projects.features.pulse.presentation)
    api(projects.features.steam.presentation)

    testImplementation(libs.ktor.server.test.host)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}