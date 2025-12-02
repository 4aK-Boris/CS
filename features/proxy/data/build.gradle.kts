plugins {
    id("koin.config")
    id("detekt.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.proxy.domain)
}