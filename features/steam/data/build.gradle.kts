plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    implementation(libs.jsoup)

    api(projects.core.system)
    api(projects.core.network)

    api(projects.features.steam.domain)
}