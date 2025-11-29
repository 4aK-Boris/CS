package dmitriy.losev.cs

import dmitriy.losev.cs.di.ServicesNetworkModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        ServicesNetworkModule::class,
        CacheModule::class,
        DatabaseModule::class,
        MarketFeatureModule::class,
        DatabaseFeatureModule::class,
        FloatFeatureModule::class,
        PulseFeatureModule::class,
        DataStoreFeatureModule::class,
        FilesFeatureModule::class,
        SteamFeatureModule::class,
    ]
)
class AppModule