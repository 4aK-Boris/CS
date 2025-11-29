package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [FloatDataMapperModule::class, FloatDataRepositoryModule::class, FloatDataNetworkModule::class])
class FloatDataModule