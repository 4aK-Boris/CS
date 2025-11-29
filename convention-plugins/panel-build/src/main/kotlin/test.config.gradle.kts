plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

val libs: VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {

    add(configurationName = "testImplementation", dependencyNotation = libs.findLibrary("koin-test").get())
    add(configurationName = "testImplementation", dependencyNotation = libs.findLibrary("coroutines-test").get())
    add(configurationName = "testImplementation", dependencyNotation = libs.findLibrary("kotlin-test").get())
    add(configurationName = "testImplementation", dependencyNotation = libs.findLibrary("mockk-core").get())
}

tasks.test {
    useJUnitPlatform()
}