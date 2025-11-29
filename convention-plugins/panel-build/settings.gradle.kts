pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.2.20"
        id("com.google.devtools.ksp") version "2.2.20-2.0.3"
        id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
        id("com.google.protobuf") version "0.9.5"
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}


rootProject.name = "panel-build"
