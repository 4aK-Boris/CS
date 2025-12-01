plugins {
    id("org.jetbrains.kotlin.jvm")
}

val libs: VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("ktor-server-core").get())

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("jobrunr").get())

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("konform").get())

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("smiley4-openapi").get())
    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("smiley4-swagger").get())

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("smiley4-schema-kenerator-core").get())
    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("smiley4-schema-kenerator-serialization").get())
    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("smiley4-schema-kenerator-swagger").get())
}