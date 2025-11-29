package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [PulseDataMapperModule::class, PulseDataNetworkModule::class, PulseDataRepositoryModule::class])
class PulseDataModule