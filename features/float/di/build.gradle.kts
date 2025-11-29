plugins {
    id("koin.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.float.data)
    api(projects.features.float.domain)
}