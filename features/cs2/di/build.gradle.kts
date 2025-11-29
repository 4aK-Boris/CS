plugins {
    id("koin.config")
    id("di.test.config")
}

dependencies {
    api(projects.features.cs2.data)
    api(projects.features.cs2.domain)
}