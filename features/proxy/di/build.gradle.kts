plugins {
    id("koin.config")
    id("detekt.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.proxy.data)
    api(projects.features.proxy.domain)
    api(projects.features.proxy.presentation)
}