package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [MarketDataMapperModule::class, MarketDataRepositoryModule::class, MarketDataNetworkModule::class])
class MarketDataModule