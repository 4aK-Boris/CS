package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [SteamDataMapperModule::class, SteamDataRepositoryModule::class])
class SteamDataModule