package dmitriy.losev.cs

import dmitriy.losev.cs.di.CSDataModule
import dmitriy.losev.cs.di.CSDomainModule
import org.koin.core.annotation.Module

@Module(includes = [CSDataModule::class, CSDomainModule::class])
class CSFeatureModule