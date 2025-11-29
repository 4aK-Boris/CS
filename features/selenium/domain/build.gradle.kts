plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dmitriy.losev.cs"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}