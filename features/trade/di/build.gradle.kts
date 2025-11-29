plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.features.trade.domain)
    api(projects.features.trade.presentation)
}