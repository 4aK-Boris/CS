package dmitriy.losev.cs.di

import dmitriy.losev.cs.HMacCrypto
import dmitriy.losev.cs.RsaCrypto
import dmitriy.losev.cs.TimeHandler
import dmitriy.losev.cs.core.MultiParmFormParamsCreator
import dmitriy.losev.cs.core.OpenIdExtractor
import dmitriy.losev.cs.core.SteamGuard
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module(includes = [SteamDataMapperModule::class, SteamDataRepositoryModule::class, SteamDataNetworkModule::class])
class SteamDataModule {

    @Singleton
    internal fun getSteamGuard(@Provided timeHandler: TimeHandler, @Provided rsaCrypto: RsaCrypto, @Provided hMacKeySpec: HMacCrypto): SteamGuard {
        return SteamGuard(timeHandler, rsaCrypto, hMacKeySpec)
    }

    @Singleton
    internal fun getOpenIdExtractor(): OpenIdExtractor {
        return OpenIdExtractor()
    }

    @Singleton
    internal fun getMultiParmFormParamsCreator(): MultiParmFormParamsCreator {
        return MultiParmFormParamsCreator()
    }
}
