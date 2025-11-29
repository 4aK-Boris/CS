plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.core)

    api(projects.features.datastore.domain)
    api(projects.features.database.domain)
}