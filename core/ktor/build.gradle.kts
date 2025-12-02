plugins {
    id("koin.config")
    id("test.config")
    id("detekt.config")
    id("serialization.config")
    alias(libs.plugins.ktor)
}

dependencies {

    implementation(libs.ktor.server.core)

    implementation(libs.logback.classic)

    implementation(libs.jobrunr)

    implementation(libs.konform)

    implementation(libs.smiley4.openapi)
    implementation(libs.smiley4.swagger)

    api(projects.core)

    testImplementation(libs.ktor.server.test.host)
}