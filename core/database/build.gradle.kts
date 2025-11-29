plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.json)
    implementation(libs.exposed.java.time)

    implementation(libs.r2dbc.pool)
    implementation(libs.r2dbc.postgresql)

    api(projects.core)
}