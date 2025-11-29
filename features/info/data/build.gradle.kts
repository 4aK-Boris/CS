plugins {
    id("koin.config")
    id("test.config")
    id("protobuf.config")
}

dependencies {

    implementation(libs.java.steam)

    implementation("org.bouncycastle:bcpkix-jdk18on:1.82")

    api(projects.core.system)
    api(projects.features.info.domain)
}

sourceSets {
    main {
        proto {
            srcDir("${rootProject.projectDir}/features/info/data/src/main/resources/proto")
        }
        java {
            srcDirs("build/generated/sources/proto/main/java")
        }
    }
}
