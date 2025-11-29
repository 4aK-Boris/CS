package dmitriy.losev.cs

import dmitriy.losev.cs.di.DataStoreDataModule
import dmitriy.losev.cs.di.DataStoreDomainModule
import org.koin.core.annotation.Module

@Module(includes = [DataStoreDataModule::class, DataStoreDomainModule::class])
class DataStoreFeatureModule