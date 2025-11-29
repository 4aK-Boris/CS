plugins {
    id("koin.config")
    id("test.config")
}

dependencies {

   api(projects.features.pulse.domain)
}