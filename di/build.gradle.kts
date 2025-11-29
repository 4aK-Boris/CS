plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.core.network)
    api(projects.core.cache)

    api(projects.features.database.di)
    api(projects.features.market.di)
    api(projects.features.float.di)
    api(projects.features.pulse.di)
    api(projects.features.datastore.di)
    api(projects.features.files.di)
    api(projects.features.steam.di)
}