plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val libs: VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("serialization").get())
}