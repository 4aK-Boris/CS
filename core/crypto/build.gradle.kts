plugins {
    id("koin.config")
    id("detekt.config")
    id("test.config")
}

dependencies {

    api(projects.core)
}
