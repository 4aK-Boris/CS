plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.core.system)
    api(projects.features.remote.domain)
}