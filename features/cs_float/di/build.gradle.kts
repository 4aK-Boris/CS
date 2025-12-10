plugins {
    id("koin.config")
    id("detekt.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.csFloat.data)
    api(projects.features.csFloat.domain)
}