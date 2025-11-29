plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    api(projects.core)
    api(projects.core.database)

    api(libs.caffeine)
}
