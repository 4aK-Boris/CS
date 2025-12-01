plugins {
    id("koin.config")
    id("ktor.config")
    id("detekt.config")
    id("test.config")
    id("serialization.config")
    alias(libs.plugins.ktor)
}

group = "dmitriy.losev.cs"
version = "0.0.1"

dependencies {

    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.double.receive)
    implementation(libs.ktor.serialization.json)

    implementation(libs.logback.classic)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    api(projects.di)
//
//    api(projects.features.pulse.presentation)
    api(projects.features.steam.presentation)

    testImplementation(libs.ktor.server.test.host)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}