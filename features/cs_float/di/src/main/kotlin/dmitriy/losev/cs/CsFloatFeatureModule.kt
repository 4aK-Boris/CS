package dmitriy.losev.cs

import dmitriy.losev.cs.di.CsFloatDataModule
import dmitriy.losev.cs.di.CsFloatDomainModule
import org.koin.core.annotation.Module

@Module(includes = [CsFloatDataModule::class, CsFloatDomainModule::class])
class CsFloatFeatureModule
