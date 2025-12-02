plugins {
    id("koin.config")
    id("detekt.config")
    id("serialization.config")
    id("test.config")
}

dependencies {

    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.json)
    implementation(libs.exposed.java.time)

    implementation(libs.r2dbc.pool)
    implementation(libs.r2dbc.postgresql)

    api(projects.core)
    api(projects.core.crypto)
}