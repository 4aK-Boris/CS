package dmitriy.losev.cs

import dmitriy.losev.cs.di.CoreSystemModule
import dmitriy.losev.cs.di.RemoteDataModule
import dmitriy.losev.cs.di.RemoteDomainModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        RemoteDataModule::class,
        RemoteDomainModule::class,
        SteamFeatureModule::class,
        CSFeatureModule::class,
        CoreSystemModule::class
    ]
)
class RemoteFeatureModule