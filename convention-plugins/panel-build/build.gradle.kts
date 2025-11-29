import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "panel-build"

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

kotlin {

    jvmToolchain(jdkVersion = 21)

    compilerOptions {
        jvmTarget = JvmTarget.fromTarget("21")
    }
}

dependencies {

    implementation(gradleApi())

    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.2.20")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.5")
}


