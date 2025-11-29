plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    implementation(libs.kotlin.date.time)

    api(projects.core.network)

    api(projects.features.lisskins.domain)
}