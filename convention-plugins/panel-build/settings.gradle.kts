pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/ktor/eap/")
    }
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.2.21"
        id("com.google.devtools.ksp") version "2.3.3"
        id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"
        id("com.google.protobuf") version "0.9.5"
        id("io.gitlab.arturbosch.detekt") version "1.23.8"
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/ktor/eap/")
    }
}


rootProject.name = "panel-build"
