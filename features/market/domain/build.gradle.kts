plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.core)

    api(projects.features.database.domain)
    api(projects.features.float.domain)

    api(projects.shared.marketDatabase)
}