plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    implementation(libs.datastore.core)

    api(projects.core.system)
    api(projects.features.datastore.domain)
}