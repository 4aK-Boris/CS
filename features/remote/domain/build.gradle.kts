plugins {
    id("koin.config")
    id("test.config")
}

dependencies {
    api(projects.core)
    api(projects.features.steam.domain)
    api(projects.features.cs2.domain)
}