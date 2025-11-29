plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.features.marketCsgo.data)
    api(projects.features.marketCsgo.domain)
}