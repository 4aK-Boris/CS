package dmitiry.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [ProxyDataMapperModule::class, ProxyDataRepositoryModule::class])
class ProxyDataModule
