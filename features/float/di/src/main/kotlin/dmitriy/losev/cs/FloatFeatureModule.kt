package dmitriy.losev.cs

import dmitriy.losev.cs.di.FloatDataModule
import dmitriy.losev.cs.di.FloatDomainModule
import org.koin.core.annotation.Module

@Module(includes = [FloatDataModule::class, FloatDomainModule::class])
class FloatFeatureModule