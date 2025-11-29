plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    api(projects.features.files.data)
    api(projects.features.files.domain)
}