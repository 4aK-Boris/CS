plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.core)

    api(projects.shared.marketDatabase)
}