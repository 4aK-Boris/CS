package dmitriy.losev.cs

import dmitriy.losev.cs.di.DatabaseDataModule
import dmitriy.losev.cs.di.DatabaseDomainModule
import dmitriy.losev.cs.di.DatabaseSourceModule
import org.koin.core.annotation.Module

@Module(includes = [DatabaseSourceModule::class, DatabaseDataModule::class, DatabaseDomainModule::class])
class DatabaseFeatureModule