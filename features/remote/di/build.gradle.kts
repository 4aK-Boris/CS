plugins {
    id("koin.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.remote.data)
    api(projects.features.remote.domain)

    api(projects.features.steam.di)
    api(projects.features.cs2.di)
}