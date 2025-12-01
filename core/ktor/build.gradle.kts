plugins {
    id("koin.config")
    id("test.config")
    id("detekt.config")
    id("serialization.config")
    id("io.ktor.plugin") version "3.3.3"
}

dependencies {

    implementation(libs.ktor.server.core)

    implementation(libs.logback.classic)

    implementation(libs.jobrunr)

    implementation(libs.konform)

    implementation(libs.smiley4.openapi)
    implementation(libs.smiley4.swagger)

    testImplementation(libs.ktor.server.test.host)
}