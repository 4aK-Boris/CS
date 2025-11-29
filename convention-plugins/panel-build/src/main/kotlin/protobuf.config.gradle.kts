plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.protobuf")
}

val libs: VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

dependencies {

    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("java-protobuf").get())
    add(configurationName = "implementation", dependencyNotation = libs.findLibrary("kotlin-protobuf").get())
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.33.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                named("java")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

// Ensure protobuf files are generated before KSP processing
tasks.matching { it.name == "kspKotlin" }.configureEach {
    dependsOn("generateProto")
}
