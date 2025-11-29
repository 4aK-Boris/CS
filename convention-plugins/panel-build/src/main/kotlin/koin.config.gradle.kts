plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(jdkVersion = 21)
}

val libs: VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("coroutines").get())

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("koin-core").get())
    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("koin-annotations").get())
    add(configurationName = "ksp", dependencyNotation = libs.findLibrary("koin-ksp-compiler").get())
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
    arg("KOIN_DEFAULT_MODULE", "true")
    arg("excludeProtoGenerated", "true")
}