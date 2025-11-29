plugins {
    id("koin.config")
    id("test.config")
}

dependencies {
    implementation(libs.jna.core)
    implementation(libs.jna.platform)

    api(projects.core)
}
