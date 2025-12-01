package dmitriy.losev.cs

import dmitriy.losev.cs.di.DatabaseDataModule
import dmitriy.losev.cs.di.DatabaseDomainModule
import org.koin.core.annotation.Module

@Module(includes = [DatabaseDataModule::class, DatabaseDomainModule::class])
class DatabaseFeatureModule
