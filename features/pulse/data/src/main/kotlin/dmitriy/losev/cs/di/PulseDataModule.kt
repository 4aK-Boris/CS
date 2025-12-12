package dmitriy.losev.cs.di

import dmitriy.losev.cs.core.MarketPriceUpdater
import dmitriy.losev.cs.core.TokenHolder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [PulseDataMapperModule::class, PulseDataNetworkModule::class, PulseDataRepositoryModule::class])
class PulseDataModule {

    @Singleton
    internal fun getTokenHolder(): TokenHolder {
        return TokenHolder()
    }

    @Singleton
    internal fun getMarketPriceUpdater(): MarketPriceUpdater {
        return MarketPriceUpdater()
    }
}
