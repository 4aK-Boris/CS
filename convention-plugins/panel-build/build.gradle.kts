import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "panel-build"

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

kotlin {

    jvmToolchain(jdkVersion = 21)

    compilerOptions {
        jvmTarget = JvmTarget.fromTarget("21")
    }
}

dependencies {

    implementation(gradleApi())

    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.3.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.2.21")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.5")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
}


