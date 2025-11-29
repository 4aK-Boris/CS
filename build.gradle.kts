plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("io.ktor.plugin") version "3.3.0"
}

group = "dmitriy.losev.cs"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty")
    //implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.seleniumhq.selenium:selenium-java:4.36.0")
    implementation("org.seleniumhq.selenium:selenium-devtools-v140:4.36.0")

    implementation("io.github.bonigarcia:webdrivermanager:6.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}
