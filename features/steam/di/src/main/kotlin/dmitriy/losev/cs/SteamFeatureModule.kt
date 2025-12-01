package dmitriy.losev.cs

import dmitriy.losev.cs.di.SteamDataModule
import dmitriy.losev.cs.di.SteamDomainModule
import dmitriy.losev.cs.di.SteamPresentationModule
import org.koin.core.annotation.Module

@Module(includes = [SteamDataModule::class, SteamDomainModule::class, SteamPresentationModule::class])
class SteamFeatureModule
