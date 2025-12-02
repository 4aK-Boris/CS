package dmitriy.losev.cs

import dmitiry.losev.cs.di.ProxyDataModule
import dmitriy.losev.cs.di.ProxyDomainModule
import dmitriy.losev.cs.di.ProxyPresentationModule
import org.koin.core.annotation.Module

@Module(includes = [ProxyDataModule::class, ProxyDomainModule::class, ProxyPresentationModule::class])
class ProxyFeatureModule
