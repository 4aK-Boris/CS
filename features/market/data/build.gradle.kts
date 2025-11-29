plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    api(projects.core.network)
    api(projects.features.market.domain)
}
