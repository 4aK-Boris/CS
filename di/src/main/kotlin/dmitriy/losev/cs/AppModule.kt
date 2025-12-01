package dmitriy.losev.cs

import dmitriy.losev.cs.di.CryptoCoreModule
import dmitriy.losev.cs.di.DatabaseCoreModule
import dmitriy.losev.cs.di.ScheduleCoreModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        //ServicesNetworkModule::class,
        DatabaseFeatureModule::class,
        //FloatFeatureModule::class,
        SteamFeatureModule::class,
        CryptoCoreModule::class,
        ScheduleCoreModule::class,
        DatabaseCoreModule::class,
    ]
)
class AppModule
