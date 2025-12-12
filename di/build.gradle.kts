plugins {
    id("koin.config")
    id("test.config")
    id("detekt.config")
}

dependencies {

    api(projects.core.network)
    api(projects.core.database)
    api(projects.core.ktor)
    api(projects.core.crypto)
    api(projects.core.schedule)
    api(projects.core.system)

    api(projects.features.database.di)
    //api(projects.features.market.di)
    //api(projects.features.float.di)
    api(projects.features.pulse.di)
    //api(projects.features.datastore.di)
    //api(projects.features.files.di)
    api(projects.features.steam.di)
    api(projects.features.proxy.di)
    api(projects.features.csFloat.di)
}