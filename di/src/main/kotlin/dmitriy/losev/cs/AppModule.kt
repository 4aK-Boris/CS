package dmitriy.losev.cs

import dmitriy.losev.cs.di.CryptoCoreModule
import dmitriy.losev.cs.di.DatabaseCoreModule
import dmitriy.losev.cs.di.ScheduleCoreModule
import dmitriy.losev.cs.di.ServicesNetworkModule
import dmitriy.losev.cs.di.SystemCoreModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        ServicesNetworkModule::class,
        CryptoCoreModule::class,
        ScheduleCoreModule::class,
        DatabaseCoreModule::class,
        SystemCoreModule::class,
        DatabaseFeatureModule::class,
        SteamFeatureModule::class,
        ProxyFeatureModule::class,
        CsFloatFeatureModule::class,
        PulseFeatureModule::class
    ]
)
class AppModule
