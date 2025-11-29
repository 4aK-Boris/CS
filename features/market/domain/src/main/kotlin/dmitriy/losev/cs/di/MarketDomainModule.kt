package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [MarketDomainUseCaseModule::class])
class MarketDomainModule