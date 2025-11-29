plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.features.pulse.data)
    api(projects.features.pulse.domain)
    api(projects.features.pulse.presentation)
}