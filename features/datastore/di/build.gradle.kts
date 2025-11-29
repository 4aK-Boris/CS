plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.features.datastore.data)
    api(projects.features.datastore.domain)
}