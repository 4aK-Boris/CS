package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [CsFloatDataMapperModule::class, CsFloatDataNetworkModule::class, CsFloatDataRepositoryModule::class])
class CsFloatDataModule
