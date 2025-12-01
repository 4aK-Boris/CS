plugins {
    id("koin.config")
    id("ktor.config")
    id("test.config")
    id("detekt.config")
    id("serialization.config")
}

dependencies {

    api(projects.core.ktor)

    api(projects.features.steam.domain)
}