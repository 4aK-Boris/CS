plugins {
    id("koin.config")
    id("detekt.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.steam.data)
    api(projects.features.steam.domain)
    api(projects.features.steam.presentation)
}