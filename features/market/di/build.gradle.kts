plugins {
    id("koin.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.market.data)
    api(projects.features.market.domain)
}