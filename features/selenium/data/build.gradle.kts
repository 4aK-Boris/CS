plugins {
    id("koin.config")
    id("serialization.config")
    id("test.config")
}

dependencies {

    implementation(libs.selenium.java)
    implementation(libs.selenium.devtool)

    implementation(libs.webdriver.manager)

    implementation("net.java.dev.jna:jna:5.18.1")
    implementation("net.java.dev.jna:jna-platform:5.18.1")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.13")

    implementation("com.github.kwhat:jnativehook:2.2.2")

    implementation("io.ktor:ktor-server-core:3.3.0")
    implementation("io.ktor:ktor-server-netty:3.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.0")

    api(projects.features.selenium.domain)
}