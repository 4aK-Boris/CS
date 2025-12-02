plugins {
    id("koin.config")
    id("test.config")
    id("detekt.config")
}

dependencies {
    api(projects.core)
    api(projects.features.database.domain)
}