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
    implementation(libs.ktor.serialization.json)

    implementation(libs.logback.classic)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    testImplementation(libs.ktor.server.test.host)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}