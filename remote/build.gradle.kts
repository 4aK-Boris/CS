plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.di)

    api(projects.features.remote.domain)
    api(projects.features.database.domain)
    api(projects.features.market.domain)
}