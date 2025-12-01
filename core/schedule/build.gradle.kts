plugins {
    id("koin.config")
    id("test.config")
    id("detekt.config")
    id("serialization.config")
}

dependencies {

    api(projects.core)
}