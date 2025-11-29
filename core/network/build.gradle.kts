plugins {
    id("koin.config")
    id("test.config")
    id("serialization.config")
}

dependencies {

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.encoding)
    implementation(libs.ktor.client.auth)

    implementation(libs.logback.classic)

    api(projects.core)
    api(projects.core.cache)

    testImplementation(libs.okhttp3.mockserver)
    testImplementation(libs.ktor.client.mock)
}

tasks.test {

    reports {
        junitXml.required.set(true)
        html.required.set(true)
    }

    testLogging {

        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
        showExceptions = true
        showCauses = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }

    jvmArgs("-ea")
}

// Задача для тестирования прокси
tasks.register<JavaExec>("testProxy") {
    group = "verification"
    description = "Test all configured proxies"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("dmitriy.losev.cs.TestProxyKt")
}