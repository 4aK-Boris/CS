plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

    implementation(libs.kotlin.refrect)

    implementation(libs.logback.classic)
}