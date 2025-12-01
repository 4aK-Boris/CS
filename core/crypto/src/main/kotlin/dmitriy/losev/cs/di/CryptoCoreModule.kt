package dmitriy.losev.cs.di

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.Context
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module
class CryptoCoreModule {

    @Singleton
    fun getAesCrypto(@Provided context: Context): AesCrypto {
        return AesCrypto(context)
    }
}
