plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.di)

    api(projects.features.pulse.presentation)
    api(projects.features.steam.presentation)
}