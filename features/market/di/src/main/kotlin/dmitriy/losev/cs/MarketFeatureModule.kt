package dmitriy.losev.cs

import dmitriy.losev.cs.di.MarketDataModule
import dmitriy.losev.cs.di.MarketDomainModule
import org.koin.core.annotation.Module

@Module(includes = [MarketDataModule::class, MarketDomainModule::class])
class MarketFeatureModule