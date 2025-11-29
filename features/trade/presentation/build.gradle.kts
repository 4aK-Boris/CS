plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    api(projects.features.trade.domain)
}