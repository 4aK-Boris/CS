plugins {
    id("koin.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.database.data)
    api(projects.features.database.domain)
}